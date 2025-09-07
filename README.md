# Medical visit
## Applicaztivo per la gestione delle visite mediche,
Funzioni:
 - upload manuale di Exel con elenco Atltete e scadenza visita medica
 - visualizzazione a video della lista ordinabile per campo degli atleti
 - aggiornamento a database dei dati
 - censimento Mail a cui inviare i report
 - invio mail per ricordare delle visite mediche (cron giornaliero) oppure invio messaggio whatsup
 - cron per aggiornamento automatico dei dati.. come li recupero???
 - altre funzioni eventuali
 - gesione presenze?? 



## BE
Quarkus
 - [X] Aggiunta servizio Rest recupero dati da excel
 - [X] Test Servizio
 - [X] creato Databse Postgres su server raspberry
 - [X] Aggiunta tabella nel db
 - [X] Aggiunta service per insert/update
 - [X] Aggiunto servizio per ricercare singola atleta
TODO:
 - [ ] parametrizzare le env per il database  
 - [ ] aggiungere servizio per update manuale Massiva
 - [ ] aggiungere servizio per update puntuale
 - [ ] Aggiungere crontab per aggiornamento automatico
 - [ ] Aggiugere gestione mail/messaggistica
 - [ ] verificare perchè non c'è il risultato della ricerca


## FE
VueJs
- Aggiunto upload file + base servizio

TODO:
 - [ ] Auth (aggiunta a database), form di registrazione???
 - [ ] Gestire aggionramento dati a database
 - [ ] recupero dati da Db
 - [ ] Aggiornamento puntuale dei dati
 - 
