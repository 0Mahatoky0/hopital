// 1. Initialisation de la carte Leaflet
let map = L.map("map", {
  center: [-18.8934916, 47.5584519],
  zoom: 10,
});

// 2. Ajout du fond de carte OpenStreetMap
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap",
}).addTo(map);

// 3. Fonctions de style pour les communes
// Palette conservée mais avec un cas dédié pour "0 centre" (donnée réelle)
// distinct du blanc, pour ne pas le confondre avec le fond de carte.
function getColor(ratio) {
  if (ratio === null || ratio === undefined || isNaN(ratio)) return "#cfcfcf"; // gris = pas de donnée
  return ratio > 15
    ? "#006d2c"
    : ratio > 10
    ? "#2ea169"
    : ratio > 5
    ? "#66c2a4"
    : ratio > 0
    ? "#b2e2c8"
    : "#fdd3a5"; // orange clair = 0 centre (vraie donnée, pas une absence de donnée)
}

// Formatage lisible des nombres (espaces pour les milliers, à la française)
function formatNombre(valeur) {
  if (valeur === null || valeur === undefined || isNaN(valeur)) return "N/A";
  return Number(valeur).toLocaleString("fr-FR");
}

function formatRatio(ratio) {
  if (ratio === null || ratio === undefined || isNaN(ratio)) return "N/A";
  return Number(ratio).toFixed(2);
}

// Style par défaut et style au survol, pour bien distinguer les communes
function styleCommune(feature) {
  return {
    fillColor: getColor(feature.properties.ratio),
    weight: 1,
    opacity: 0.9,
    color: "#4a4a4a", // gris foncé, moins agressif que le noir pur
    fillOpacity: 0.65,
  };
}

function styleSurvol() {
  return {
    weight: 2.5,
    color: "#1a1a1a",
    fillOpacity: 0.8,
  };
}

let communesLayer; // référence globale pour la gestion du survol

// 4. Chargement séquentiel des couches (Communes puis Points)
fetch("/api/communes/ratios")
  .then((response) => response.json())
  .then((data) => {
    const geojsonFeatures = data.map((commune) => {
      return {
        type: "Feature",
        geometry: commune.geom,
        properties: {
          nom: commune.nomCommune,
          district: commune.district,
          region: commune.region,
          population: commune.nbPopulation,
          centres: commune.nombreCentres,
          ratio: commune.ratioPour10000Hab,
        },
      };
    });

    communesLayer = L.geoJSON(geojsonFeatures, {
      style: styleCommune,
      onEachFeature: function (feature, layer) {
        const p = feature.properties;

        // Popup détaillé au clic
        layer.bindPopup(
          `<div style="font-size:13px; line-height:1.5;">
            <b style="font-size:14px;">${p.nom}</b><br>
            <span style="color:#666;">${p.district} — ${p.region}</span>
            <hr style="margin:6px 0; border:none; border-top:1px solid #ddd;">
            <b>👥 Population :</b> ${formatNombre(p.population)} hab.<br>
            <b>🏥 Centres de santé :</b> ${formatNombre(p.centres)}<br>
            <b>📊 Ratio :</b> ${formatRatio(p.ratio)} pour 10 000 hab.
          </div>`
        );

        // Tooltip léger au survol (lecture rapide sans avoir à cliquer)
        layer.bindTooltip(
          `<b>${p.nom}</b><br>Ratio : ${formatRatio(p.ratio)} / 10 000 hab.`,
          { sticky: true }
        );

        // Mise en surbrillance de la commune survolée
        layer.on("mouseover", function () {
          layer.setStyle(styleSurvol());
          layer.bringToFront();
        });
        layer.on("mouseout", function () {
          communesLayer.resetStyle(layer);
        });

        // Le tooltip (survol) ne doit pas rester affiché en même temps
        // que le popup (clic) : ils se chevauchent sinon.
        layer.on("popupopen", function () {
          layer.closeTooltip();
        });
      },
    }).addTo(map);

    // Une fois les communes ajoutées, on demande le chargement des points
    return fetch("/geojson/info_centre.geojson");
  })
  .then((response) => response.json())
  .then((data) => {
    // Ajout des points des centres de santé au premier plan
    L.geoJSON(data, {
      pointToLayer: function (feature, latlng) {
        return L.circleMarker(latlng, {
          radius: 6,
          color: "#b91c1c",
          weight: 1.5,
          fillColor: "#f97316",
          fillOpacity: 1,
        });
      },
      onEachFeature: function (feature, layer) {
        const nom = feature.properties.name || "Centre de santé";
        const type = feature.properties.amenity || "Non renseigné";

        layer.bindPopup(
          `<div style="font-size:13px; line-height:1.5;">
            <b>🏥 ${nom}</b><br>
            <span style="color:#666;">Type : ${type}</span>
          </div>`
        );
        layer.bindTooltip(nom, { sticky: true });

        // Même règle que pour les communes : pas de tooltip pendant
        // qu'un popup est ouvert.
        layer.on("popupopen", function () {
          layer.closeTooltip();
        });
      },
    }).addTo(map);
  })
  .catch((error) =>
    console.error("Erreur lors du chargement des calques :", error)
  );

// 5. Légende du ratio (population / centres de santé)
let legend = L.control({ position: "bottomright" });

legend.onAdd = function () {
  const div = L.DomUtil.create("div", "info legend");
  div.style.background = "white";
  div.style.padding = "10px 12px";
  div.style.borderRadius = "6px";
  div.style.boxShadow = "0 1px 6px rgba(0,0,0,0.3)";
  div.style.fontSize = "12.5px";
  div.style.lineHeight = "20px";
  div.style.maxWidth = "200px";

  const paliers = [
    { couleur: "#cfcfcf", label: "Donnée manquante" },
    { couleur: "#fdd3a5", label: "0 centre" },
    { couleur: "#b2e2c8", label: "0 – 5" },
    { couleur: "#66c2a4", label: "5 – 10" },
    { couleur: "#2ea169", label: "10 – 15" },
    { couleur: "#006d2c", label: "15 et +" },
  ];

  let html = "<b>Ratio pour 10 000 hab.</b><br>";
  paliers.forEach((p) => {
    html += `<i style="background:${p.couleur}; width:14px; height:14px; display:inline-block; margin-right:6px; border:1px solid #999; vertical-align:middle;"></i>${p.label}<br>`;
  });

  html += `<hr style="margin:6px 0; border:none; border-top:1px solid #ddd;">
    <i style="background:#f97316; border:1.5px solid #b91c1c; width:12px; height:12px; border-radius:50%; display:inline-block; margin-right:7px; vertical-align:middle;"></i>Centre de santé`;

  div.innerHTML = html;
  return div;
};

legend.addTo(map);

// 6. Gestion du bouton de géolocalisation
let findPosButton = L.Control.extend({
  options: {
    position: "bottomleft",
  },

  onAdd: function (map) {
    let container = L.DomUtil.create(
      "div",
      "leaflet-bar leaflet-control leaflet-custom-control"
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