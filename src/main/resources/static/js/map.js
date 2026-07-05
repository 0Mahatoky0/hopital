let map = L.map("map", {
  center: [-18.8934916, 47.5584519],
  zoom: 10,
});

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap",
}).addTo(map);

// ---- Couche des centres de santé ----
fetch("/geojson/info_centre.geojson")
  .then((response) => response.json())
  .then((data) => {
    L.geoJSON(data, {
      pointToLayer: function (feature, latlng) {
        return L.circleMarker(latlng, {
          radius: 8,
          color: "red",
          fillColor: "orange",
          fillOpacity: 1,
        });
      },
      onEachFeature: function (feature, layer) {
        layer.bindPopup(
          "<b>" +
            feature.properties.name +
            "</b><br>" +
            feature.properties.amenity,
        );
      },
    }).addTo(map);
  });

// ---- Bouton "Position" ----
let findPosButton = L.Control.extend({
  options: {
    position: "bottomleft",
  },

  onAdd: function (map) {
    let container = L.DomUtil.create(
      "div",
      "leaflet-bar leaflet-control leaflet-custom-control",
    );
    let button = L.DomUtil.create("button", "", container);
    button.innerHTML = "Position";
    button.style.backgroundColor = "white";
    button.style.padding = "5px 10px";
    button.style.cursor = "pointer";

    L.DomEvent.on(button, "click", function (e) {
      L.DomEvent.stopPropagation(e);
      findPosition(map);
    });

    return container;
  },
});

let control = new findPosButton();
control.addTo(map);

function findPosition(map) {
  map.locate({
    setView: true,
    maxZoom: 16,
  });

  map.on("locationfound", function (event) {
    map.flyTo(event.latlng, 16);
  });
}

// =========================================================================
// PARTIE 3 : Choix du mode de division graphique de la carte
// (district / province / commune)
// =========================================================================

const DIVISIONS = {
  province: { url: "/api/carte/division?type=PROVINCE", color: "#1d4ed8", label: "Province" },
  district: { url: "/api/carte/division?type=DISTRICT", color: "#059669", label: "District" },
  commune:  { url: "/api/carte/division?type=COMMUNE",  color: "#d97706", label: "Commune" },
};

let currentAdminLayer = null;
let currentDivisionType = null;
const geojsonCache = {};

function styleDivision(color) {
  return { color, weight: 1.5, fillColor: color, fillOpacity: 0.08 };
}

function onEachDivisionFeature(feature, layer) {
  const props = feature.properties || {};
  layer.bindPopup("<b>" + (props.nom || "Inconnu") + "</b>");
  layer.on("mouseover", () => layer.setStyle({ weight: 3, fillOpacity: 0.2 }));
  layer.on("mouseout", () => layer.setStyle({ weight: 1.5, fillOpacity: 0.08 }));
}

let requestId = 0; // permet d'ignorer les réponses réseau devenues obsolètes

function afficherDivision(type) {
  if (currentDivisionType === type) return;
  const config = DIVISIONS[type];
  if (!config) return;

  const thisRequest = ++requestId;
  currentDivisionType = type;
  updateActiveButton(type);

  const applyLayer = (data) => {
    // Si un autre bouton a été cliqué entre-temps, on ignore cette réponse
    if (thisRequest !== requestId) return;

    // On retire la couche précédente ICI, juste avant d'ajouter la nouvelle,
    // pour être sûr de retirer celle qui est réellement affichée.
    if (currentAdminLayer) {
      map.removeLayer(currentAdminLayer);
      currentAdminLayer = null;
    }

    currentAdminLayer = L.geoJSON(data, {
      style: styleDivision(config.color),
      onEachFeature: onEachDivisionFeature,
    }).addTo(map);
  };

  if (geojsonCache[type]) {
    applyLayer(geojsonCache[type]);
    return;
  }

  fetch(config.url)
    .then((res) => {
      if (!res.ok) throw new Error("Erreur API : " + res.status);
      return res.json();
    })
    .then((data) => {
      geojsonCache[type] = data;
      applyLayer(data);
    })
    .catch((err) => {
      console.error(err);
      alert("Impossible de charger la couche '" + config.label + "'.");
    });
}

function updateActiveButton(type) {
  document.querySelectorAll(".division-btn").forEach((btn) => {
    btn.classList.toggle("active", btn.dataset.type === type);
  });
}

let divisionControl = L.Control.extend({
  options: { position: "topright" },
  onAdd: function () {
    let container = L.DomUtil.create("div", "leaflet-bar leaflet-control leaflet-division-control");
    ["province", "district", "commune"].forEach((type) => {
      let button = L.DomUtil.create("button", "division-btn", container);
      button.innerHTML = DIVISIONS[type].label;
      button.dataset.type = type;
      L.DomEvent.on(button, "click", (e) => {
        L.DomEvent.stopPropagation(e);
        afficherDivision(type);
      });
      L.DomEvent.disableClickPropagation(button);
    });
    return container;
  },
});

new divisionControl().addTo(map);