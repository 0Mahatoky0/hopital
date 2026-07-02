let map = L.map("map", {
  center: [-18.8934916, 47.5584519],
  zoom: 10,
});

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap",
}).addTo(map);

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
