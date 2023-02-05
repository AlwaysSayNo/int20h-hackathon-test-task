docker run 
    -d 
    -p 5455:5432 
    --name postgresdb 
    --env-file postgres-env 
    -v /C/Users/Admin/IntelliJProjects/int20h-hackathon-test-task/backend/.other/db:/var/lib/postgresql/data 
    postgres:15.1

docker exec -it int20h-hackathon bash

psql -U root int20h-hackathon

jdbc:postgresql://localhost:5455/int20h-hackathon
username: root, password: root 