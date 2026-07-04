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
