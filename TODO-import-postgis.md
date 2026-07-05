# TODO — Import des contours administratifs dans PostGIS

Reste à faire pour la Partie 3 (choix du mode de division graphique) : **importer les shapefiles de contours administratifs dans PostGIS**, tout le reste (entités, repositories, service, contrôleur, JS) est déjà en place.

## 1. Récupération des données

- [ X] Télécharger le jeu de données HDX (BNGRC/OCHA) : https://data.humdata.org/dataset/cod-ab-mdg
- [X ] Prendre le fichier `mdg_adm_BNGRC_OCHA_20181031_SHP.zip`
- [X ] Décompresser et identifier les 3 fichiers utiles :
  - [ X] `mdg_admbnda_adm1_...shp` → régions (= "province")
  - [X ] `mdg_admbnda_adm2_...shp` → districts
  - [X ] `mdg_admbnda_adm3_...shp` → communes

## 2. Préparation des outils

- [x] Installer PostGIS + GDAL sur la machine où tourne PostgreSQL :
  ```bash
  sudo apt update
  sudo apt install postgis postgresql-16-postgis-3 gdal-bin -y
  ```
- [x] Vérifier que `shp2pgsql` est disponible : `shp2pgsql --version`
- [x] Vérifier que `ogrinfo` est disponible : `ogrinfo --version`

## 3. Inspection des shapefiles avant import

- [ ] Lancer `ogrinfo -so` sur chaque shapefile pour connaître :
  ```bash
  ogrinfo -so mdg_admbnda_adm1_BNGRC_OCHA_20181031.shp mdg_admbnda_adm1_BNGRC_OCHA_20181031
  ```
  - [ ] Le type de géométrie (Polygon / MultiPolygon)
  - [ ] Le SRID / EPSG réel (normalement 4326)
  - [ ] Les noms exacts des champs (ex: `ADM1_EN`, `ADM1_PCODE`...)

## 4. Base de données

- [X ] Exécuter `database/sqlfile/init.sql` si pas déjà fait (création DB + extension postgis)
- [ X] Si des tables `province` / `district` / `commune` existent déjà (créées par Hibernate) :
  ```sql
  DROP TABLE IF EXISTS commune, district, province CASCADE;
  ```

## 5. Import des 3 niveaux avec `shp2pgsql`

- [ X] Import régions → table `province` :
  ```bash
  shp2pgsql -I -s 4326 -W UTF-8 mdg_admbnda_adm1_BNGRC_OCHA_20181031.shp province > province.sql
  psql -U postgres -d sig_hopital -f province.sql
  ```
- [X ] Import districts → table `district` :
  ```bash
  shp2pgsql -I -s 4326 -W UTF-8 mdg_admbnda_adm2_BNGRC_OCHA_20181031.shp district > district.sql
  psql -U postgres -d sig_hopital -f district.sql
  ```
- [ X] Import communes → table `commune` :
  ```bash
  shp2pgsql -I -s 4326 -W UTF-8 mdg_admbnda_adm3_BNGRC_OCHA_20181031.shp commune > commune.sql
  psql -U postgres -d sig_hopital -f commune.sql
  ```

## 6. Vérification de l'import

- [ X] Se connecter : `psql -U postgres -d sig_hopital`
- [X ] Vérifier la structure de chaque table :
  ```sql
  \d province
  \d district
  \d commune
  ```
- [X ] Vérifier le contenu :
  ```sql
  SELECT COUNT(*) FROM province;
  SELECT COUNT(*) FROM district;
  SELECT COUNT(*) FROM commune;
  SELECT * FROM province LIMIT 3;
  ```
- [ X] Noter le nom réel de la clé primaire (souvent `gid`, pas `id`)
- [ X] Noter le nom réel de la colonne du nom (souvent `adm1_en`, `adm2_en`, `adm3_en`)

## 7. Ajustement des entités JPA

- [ X] Mettre à jour `Province.java`, `District.java`, `Commune.java` avec `@Column(name = "...")` correspondant aux vrais noms de colonnes (`gid`, `adm1_en`, `geom`, etc.)
- [X ] Vérifier/adapter les clés étrangères (`province_id`, `district_id`) si elles n'existent pas déjà dans les tables importées — sinon les gérer côté requête (jointure spatiale `ST_Within` si pas de FK explicite)
- [X ] Mettre `spring.jpa.hibernate.ddl-auto=none` (ou vérifier que le mapping correspond exactement) pour ne pas que Hibernate casse les tables importées

## 8. Test de bout en bout

- [ ] Démarrer l'application Spring Boot
- [ ] Tester l'API : `GET /api/carte/division?type=PROVINCE`
- [ ] Tester l'API : `GET /api/carte/division?type=DISTRICT`
- [ ] Tester l'API : `GET /api/carte/division?type=COMMUNE`
- [ ] Vérifier l'affichage sur la carte Leaflet (les 3 boutons district/province/commune)
- [ ] Vérifier les popups (nom de la zone au clic)


