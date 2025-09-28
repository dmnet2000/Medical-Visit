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
 - gestione squadre?
 - Allenatori/gruppi
 - Orari palestre
 - Gestione base Utenti (profilazione + gestione password + gestione anagrafica)


## BE
Quarkus
 - [X] Aggiunta servizio Rest recupero dati da excel
 - [X] Test Servizio
 - [X] creato Databse Postgres su server raspberry
 - [X] Aggiunta tabella nel db
 - [X] Aggiunta service per insert/update
 - [X] Aggiunto servizio per ricercare singola atleta
 - [X] verificare perchè non c'è il risultato della ricerca
 - [X] Rendere Upper case la ricerca
 - [X] aggiungere servizio per update puntuale
 - [X] Aggiunta invio mail di prova
 - [X] Aggiugere gestione mail/messaggistica -->>>in corso 
 - [X] Aggiunta Inserimento e visualizzazione Allenatore
 
TODO:

 - [ ] aggiungere Ricera Allenatore
 - [ ] aggiungere Modifica + cancellazione allenatore
 - [ ] Aggiungere Anno Agonistico
 - [ ] Aggiungere Squadra 
 - [ ] Aggiungere Associazione Atlete Squadra
 - [ ] parametrizzare le env per il database  
 - [ ] aggiungere servizio per update manuale Massiva
 - [ ] Aggiungere crontab per aggiornamento automatico

 - [ ] Aggiungere servizio invio mail

 
## FE
VueJs
- [X] Aggiunto upload file + base servizio
- [X] Aggiunta ricerca a database + visualizzazione dati ricerca
- [X] aggiunta modifica puntuale certificato medico 
- [X] modificato menù con incone quasar.
- [X] Modifica data certificato
- [X] Ricerca atleti da database
- [x] Aggiungere chiamata a servizio per modificare certificato medico

TODO:
 
 - [ ] Auth (aggiunta a database), form di registrazione???
 - [ ] Gestire aggionramento dati a database
 - [X] Aggiornamento puntuale dei dati Atleta
 - [ ] Aggingere aggionramento Allenatori
 - [ ] Aggiungere Squadre
 - [ ] Aggingere associazione squadre

