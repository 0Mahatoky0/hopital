// 1. Initialisation de la carte Leaflet
let map = L.map("map", {
    center: [-18.8934916, 47.5584519],
    zoom: 10,
});

// 2. Ajout du fond de carte OpenStreetMap
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap",
}).addTo(map);

// 3. Fonctions de style pour les communes (Couleurs adoucies)
function getColor(ratio) {
    return ratio > 15 ? '#006d2c' :
        ratio > 10 ? '#2ea169' :
        ratio > 5 ? '#81dbb3' :
        ratio > 0 ? '#e7f7f0' :
        '#ffffff'; // Blanc si aucun centre
}

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
                    population: commune.population,
                    centres: commune.nombreCentres,
                    ratio: commune.ratioPour10000Hab
                }
            };
        });

        // Ajout des polygones des communes en arrière-plan
        L.geoJSON(geojsonFeatures, {
            style: function(feature) {
                return {
                    fillColor: getColor(feature.properties.ratio),
                    weight: 0.8, // Bordures très fines
                    opacity: 0.6, // Bordures semi-transparentes
                    color: '#ffffff', // Lignes blanches pour plus de douceur
                    fillOpacity: 0.35 // Remplissage discret pour voir le fond de carte
                };
            },
            onEachFeature: function(feature, layer) {
                layer.bindPopup(
                    `<b>Zone : ${feature.properties.nom}</b><br>` +
                    `District : ${feature.properties.district}<br>` +
                    `Population : ${feature.properties.population} hab.<br>` +
                    `Nombre de centres : ${feature.properties.centres}<br>` +
                    `<b>Ratio : ${Number(feature.properties.ratio).toFixed(2)} pour 10 000 hab.</b>`
                );
            }
        }).addTo(map);

        // Une fois les communes ajoutées, on demande le chargement des points
        return fetch("/geojson/info_centre.geojson");
    })
    .then((response) => response.json())
    .then((data) => {
        // Ajout des points des centres de santé au premier plan
        L.geoJSON(data, {
            pointToLayer: function(feature, latlng) {
                return L.circleMarker(latlng, {
                    radius: 6,
                    color: "red",
                    fillColor: "orange",
                    fillOpacity: 1,
                });
            },
            onEachFeature: function(feature, layer) {
                layer.bindPopup(
                    "<b>" + feature.properties.name + "</b><br>" + feature.properties.amenity
                );
            }
        }).addTo(map);
    })
    .catch((error) => console.error("Erreur lors du chargement des calques :", error));

// 5. Gestion du bouton de géolocalisation
let findPosButton = L.Control.extend({
    options: {
        position: "bottomleft",
    },

    onAdd: function(map) {
        let container = L.DomUtil.create(
            "div",
            "leaflet-bar leaflet-control leaflet-custom-control",
        );
        let button = L.DomUtil.create("button", "", container);
        button.innerHTML = "Position";
        button.style.backgroundColor = "white";
        button.style.padding = "5px 10px";
        button.style.cursor = "pointer";

        L.DomEvent.on(button, "click", function(e) {
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

    map.on("locationfound", function(event) {
        map.flyTo(event.latlng, 16);
    });
}