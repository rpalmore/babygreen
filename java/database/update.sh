#!/bin/bash
BASEDIR=$(dirname $0)
DATABASE=plant_db
psql plant_db -U postgres -f "$BASEDIR/updatedb.sql"