let userMarker = null;
let centerMarker = null;
let routeLayer = null;
let healthCentersResults = [];
let currentCenterIndex = 0;
let userLocation = null;
let amenitiesList = [];

let map = L.map("map", {
  center: [-18.8934916, 47.5584519],
  zoom: 10,
});

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap",
}).addTo(map);

fetch("http://localhost:8080/api/healthCenter/all")
  .then((response) => response.json())
  .then((data) => {
    const geojson = {
      type: "FeatureCollection",
      features: data.map((center) => ({
        type: "Feature",
        geometry: center.geometry,
        properties: {
          name: center.name,
          amenity: center.amenity.libelle,
        },
      })),
    };

    L.geoJSON(geojson, {
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

const locateControl = L.control({ position: "bottomleft" });

locateControl.onAdd = function (map) {
  const btn = L.DomUtil.create("button", "my-location-btn");

  btn.innerHTML = "Locate";
  btn.title = "Locate Me";

  L.DomEvent.disableClickPropagation(btn);

  btn.onclick = function () {
    navigator.geolocation.getCurrentPosition(function (position) {
      const lat = position.coords.latitude;
      const lon = position.coords.longitude;

      if (userMarker) map.removeLayer(userMarker);
      userMarker = L.marker([lat, lon], { title: "Votre position" }).addTo(map);
      map.setView([lat, lon], 16);
    });
  };

  return btn;
};

locateControl.addTo(map);

const searchControl = L.control({ position: "bottomleft" });

searchControl.onAdd = function (map) {
  const btn = L.DomUtil.create("button", "search-center-btn");

  btn.innerHTML = "Rechercher centre de santé";
  btn.title = "Trouver le centre le plus proche";

  L.DomEvent.disableClickPropagation(btn);

  btn.onclick = function () {
    if (!userMarker) {
      alert("Veuillez d'abord localiser votre position");
      return;
    }

    userLocation = userMarker.getLatLng();
    loadAmenitiesAndShowModal();
  };

  return btn;
};

searchControl.addTo(map);

function loadAmenitiesAndShowModal() {
  fetch("http://localhost:8080/api/amenity/all")
    .then((res) => res.json())
    .then((data) => {
      amenitiesList = data;
      showAmenitySelectionModal();
    })
    .catch((err) => {
      console.error("Erreur lors du chargement des amenities:", err);
      alert("Erreur lors du chargement des types de centre");
    });
}

function showAmenitySelectionModal() {
  let modalHTML = `
    <div class="modal-overlay" onclick="closeAmenityModal()">
      <div class="modal-content" onclick="event.stopPropagation()">
        <button class="modal-close" onclick="closeAmenityModal()">✕</button>
        
        <h2 class="modal-title">Sélectionner un type de centre</h2>
        
        <div class="amenity-options">
  `;

  amenitiesList.forEach((amenity) => {
    modalHTML += `
          <div class="amenity-option">
            <input 
              type="radio" 
              name="amenity" 
              value="${amenity.id}" 
              id="amenity-${amenity.id}"
            >
            <label for="amenity-${amenity.id}">${amenity.libelle}</label>
          </div>
    `;
  });

  modalHTML += `
        </div>

        <div class="modal-actions">
          <button class="btn-cancel" onclick="closeAmenityModal()">Annuler</button>
          <button class="btn-confirm" onclick="confirmAmenitySelection()">Valider</button>
        </div>
      </div>
    </div>
  `;

  let modalContainer = document.getElementById("amenity-modal-container");
  if (!modalContainer) {
    modalContainer = document.createElement("div");
    modalContainer.id = "amenity-modal-container";
    document.body.appendChild(modalContainer);
  }

  modalContainer.innerHTML = modalHTML;
  modalContainer.classList.add("active");
}

function closeAmenityModal() {
  const modalContainer = document.getElementById("amenity-modal-container");
  if (modalContainer) {
    modalContainer.classList.remove("active");
    modalContainer.innerHTML = "";
  }
}

function confirmAmenitySelection() {
  const selectedRadio = document.querySelector('input[name="amenity"]:checked');
  
  if (!selectedRadio) {
    alert("Veuillez sélectionner un type de centre");
    return;
  }

  const amenityId = selectedRadio.value;
  closeAmenityModal();
  getNearestHealthCenterByAmenity(userLocation.lat, userLocation.lng, amenityId);
}

function getNearestHealthCenter(lat, lon) {
  fetch("http://localhost:8080/api/healthCenter/nearest/five", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ lat, lon }),
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);

      healthCentersResults = data;
      currentCenterIndex = 0;

      displayCenterOnMap(currentCenterIndex);
    })
    .catch((err) => console.error(err));
}

function getNearestHealthCenterByAmenity(lat, lon, amenityId) {
  fetch(`http://localhost:8080/api/healthCenter/nearest/five/amenity?amenityId=${amenityId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ lat, lon }),
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);

      healthCentersResults = data;
      currentCenterIndex = 0;

      if (healthCentersResults.length === 0) {
        alert("Aucun centre de ce type trouvé près de votre position");
        return;
      }

      displayCenterOnMap(currentCenterIndex);
    })
    .catch((err) => {
      console.error(err);
      alert("Erreur lors de la recherche des centres");
    });
}

function displayCenterOnMap(index) {
  if (!healthCentersResults || healthCentersResults.length === 0) {
    console.error("Aucun centre de santé trouvé");
    return;
  }

  if (index < 0 || index >= healthCentersResults.length) {
    return;
  }

  const data = healthCentersResults[index];

  if (centerMarker) map.removeLayer(centerMarker);
  if (routeLayer) map.removeLayer(routeLayer);

  const centerCoords = data.center.geometry.coordinates;
  const centerLat = centerCoords[1];
  const centerLon = centerCoords[0];

  centerMarker = L.marker([centerLat, centerLon], {
    icon: L.icon({
      iconUrl: "https://cdn-icons-png.flaticon.com/512/684/684908.png",
      iconSize: [30, 30],
    }),
  })
    .addTo(map)
    .bindPopup(
      `
      <b>${data.center.name}</b><br>
       Centre de sante
    `,
    )
    .openPopup();

  if (data.route && data.route.geometry) {
    routeLayer = L.geoJSON(data.route.geometry, {
      style: {
        color: "#1a73e8",
        weight: 5,
        opacity: 0.9,
      },
    }).addTo(map);
  }
  
  showInfoPanel(data, index);
}

function showInfoPanel(data, index) {
  const center = data.center;
  const route = data.route;
  const totalCenters = healthCentersResults.length;

  let infoPanelHTML = `
    <div class="info-panel">
      <button class="close-btn" onclick="closeInfoPanel()">✕</button>
      
      <div class="panel-header">
        <span class="center-counter">${index + 1} / ${totalCenters}</span>
      </div>

      <div class="panel-section">
        <h3 class="panel-title">Centre de Santé</h3>
        <div class="info-item">
          <span class="info-label">Nom:</span>
          <span class="info-value">${center.name || 'N/A'}</span>
        </div>
        <div class="info-item">
          <span class="info-label">Type:</span>
          <span class="info-value">${center.amenity?.libelle || 'N/A'}</span>
        </div>
        <div class="info-item">
          <span class="info-label">Services:</span>
          <span class="info-value">${center.healthcare || 'N/A'}</span>
        </div>
        ${center.operator ? `
        <div class="info-item">
          <span class="info-label">Opérateur:</span>
          <span class="info-value">${center.operator}</span>
        </div>
        ` : ''}
        ${center.speciality ? `
        <div class="info-item">
          <span class="info-label">Spécialité:</span>
          <span class="info-value">${center.speciality}</span>
        </div>
        ` : ''}
      </div>

      ${route ? `
      <div class="panel-section">
        <h3 class="panel-title">Itinéraire</h3>
        <div class="info-item">
          <span class="info-label">Distance:</span>
          <span class="info-value">${(route.distance / 1000).toFixed(2)} km</span>
        </div>
        <div class="info-item">
          <span class="info-label">Durée estimée:</span>
          <span class="info-value">${Math.round(route.duration / 60)} min</span>
        </div>
      </div>
      ` : ''}

      <div class="panel-navigation">
        <button class="nav-btn prev-btn" onclick="previousCenter()" ${index === 0 ? 'disabled' : ''}>
          ← Précédent
        </button>
        <button class="nav-btn next-btn" onclick="nextCenter()" ${index === totalCenters - 1 ? 'disabled' : ''}>
          Suivant →
        </button>
      </div>
    </div>
  `;

  let panelContainer = document.getElementById("info-panel-container");
  if (!panelContainer) {
    panelContainer = document.createElement("div");
    panelContainer.id = "info-panel-container";
    document.body.appendChild(panelContainer);
  }

  panelContainer.innerHTML = infoPanelHTML;
}

function nextCenter() {
  if (currentCenterIndex < healthCentersResults.length - 1) {
    currentCenterIndex++;
    displayCenterOnMap(currentCenterIndex);
  }
}

function previousCenter() {
  if (currentCenterIndex > 0) {
    currentCenterIndex--;
    displayCenterOnMap(currentCenterIndex);
  }
}

function closeInfoPanel() {
  const panelContainer = document.getElementById("info-panel-container");
  if (panelContainer) {
    panelContainer.innerHTML = "";
  }
}
