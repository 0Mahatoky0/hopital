# script pour cree une backup de la base de donne
pg_dump -h localhost -U ton_user -d ton_db -F p -f backup.sql

# script pour utiliser le backup du base de donne 
psql -h localhost -U ton_user -d ton_db -f backup.sql