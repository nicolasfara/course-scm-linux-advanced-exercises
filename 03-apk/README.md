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
>
> **Tip 1**: utilizzare `apk -h` per visualizzare l'help del comando.  
> **Tip 2**: utilizzare `apk <comando> -h` per visualizzare l'help di un comando specifico.

**Tempo stimato**: 2 minuti

<details>
<summary>Soluzione</summary>
```bash
apk update
apk list -u
```
</details>

## Esercizio 02

> Aggiornare tutti i pacchetti del sistema all'ultima versione disponibile.

**Tempo stimato**: 5 minuti

<details>
<summary>Soluzione</summary>
```bash
apk upgrade
```

Esiste un flag per "simulare" l'aggiornamento senza effettuarlo:  
`apk upgrade -s` o `apk upgrade --simulate`.

</details>

## Esercizio 03

> Verificare se nei repository è disponibile il pacchetto vim.  
> Mostrare le dipendenze del pacchetto.  
> Se il pacchetto è disponibile, installarlo.

**Tempo stimato**: 5 minuti

<details>
<summary>Soluzione</summary>
```bash
apk search vim
apk info -a vim
apk add vim
```
</details>

## Esercizio 04

> Rimuovere il pacchetto vim dal sistema.

**Tempo stimato**: 2 minuti

<details>
<summary>Soluzione</summary>
```bash
apk del vim
```
</details>

## Esercizio 05

> Creare un'immagine Docker basata su Alpine Linux che:
>
> - Installa Nginx per servire una pagina web.
> - Configurare il container per supportare la configurazione [`nginx.conf`](./nginx.conf) e servire il file [`index.html`](./index.html).

**Tempo stimato**: 20 minuti

<details>
<summary>Soluzione</summary>
```Dockerfile
FROM alpine:latest

RUN apk add --no-cache nginx

COPY nginx.conf /etc/nginx/nginx.conf
COPY index.html /var/lib/nginx/index.html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

Compilare l'immagine Docker:

```bash
docker build -t scm-nginx-alpine .
```

Eseguire il container:

```bash
docker run --rm -d -p 8080:80 scm-nginx-alpine
```
</details>
