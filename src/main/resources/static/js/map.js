let userMarker = null;
let centerMarker = null;
let routeLayer = null;

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

      L.marker([lat, lon], { title: "Votre position" }).addTo(map);
      map.setView([lat, lon], 16);

      getNearestHealthCenter(lat, lon);
    });
  };

  return btn;
};

locateControl.addTo(map);

function getNearestHealthCenter(lat, lon) {
  fetch("http://localhost:8080/api/healthCenter/nearest", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ lat, lon }),
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);

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
      showInfoPanel(data);
    })
    .catch((err) => console.error(err));
}
