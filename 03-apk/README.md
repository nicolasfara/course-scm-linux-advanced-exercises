# Alpine Package Keeper (apk)

## Setup ambiente di lavoro

> Si consiglia di utilizzare `docker` per effettuare l'esercitazione: installare Alpine Linux richiede configurazioni non banali.

Effettuare l'esercitazione all'interno di un container Docker con Alpine Linux:

```bash
docker run --rm -it alpine:latest /bin/sh
```

## Esercizio 01

> Verificare se il sistema necessita di aggiornamenti.  
> Se sono disponibili aggiornamenti, mostrarne i dettagli.

## Esercizio 02

> Aggiornare tutti i pacchetti del sistema all'ultima versione disponibile.

## Esercizio 03

> Verificare se nei repository è disponibile il pacchetto vim.  
> Se il pacchetto è disponibile, installarlo e mostrarne i dettagli.

## Esercizio 04

> Rimuovere il pacchetto vim dal sistema.

## Esercizio 05

> Creare un'immagine Docker basata su Alpine Linux che:
>
> - Installa Nginx per servire una pagina web.
> - Configura htop per il monitoraggio delle risorse del sistema.
> - Personalizza la configurazione di Nginx per servire una pagina HTML personalizzata.

**Tempo stimato**: 20 minuti