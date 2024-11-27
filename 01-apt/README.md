## 01-apt

## Setup ambiente di lavoro

> Saltare questo passaggio se si dispone di una macchina con Ubuntu 22.04 o superiore già configurata.

### Windows

1. Assicurarsi di aver installato [WSL2](https://docs.microsoft.com/it-it/windows/wsl/install).
2. Installare Ubuntu 22.04 o superiore da Microsoft Store.
3. Verificare che sia disponibile un terminale con bash.

### macOs o altre distro Linux

1. Assicurarsi di avere installato Docker.
2. Installare Ubuntu 22.04 o superiore da [Docker Hub](https://hub.docker.com/_/ubuntu).
3. Eseguire il seguente comando per avviare un container con Ubuntu 22.04:

```bash
docker run --rm -it -v ubuntu:22.04 /bin/bash
```

## Esercizio 01

> Verificare se il sistema necessita di aggiornamenti.  
> Nel caso alcuni aggiornamenti siano disponibili, stampare a video la lista degli aggiornamenti.

<details>
<summary>Soluzione</summary>

```bash
sudo apt update
sudo apt list --upgradable
```
</details>

**Tempo stimato**: 5 minuti

## Esercizio 02

> Se al passo precedente sono stati trovati aggiornamenti, installarli.

<details>
<summary>Soluzione</summary>

```bash
sudo apt upgrade
```
</details>

**Tempo stimato**: 5 minuti

## Esercizio 03

> Verificare se nei repository di default esiste il pacchetto `vim`. Se il pacchetto è presente, mostrarne le informazioni dettagliate.  
> Installare il pacchetto `vim`.

<details>
<summary>Soluzione</summary>

```bash
apt search vim
apt show vim
```
</details>

**Tempo stimato**: 5 minuti

## Esercizio 04

> Rimuovere il pacchetto `vim` dal sistema.

<details>
<summary>Soluzione</summary>

```bash
apt remove vim
```
</details>


**Tempo stimato**: 2 minuti

## Esercizio 05

> Installare `mongodb` sul sistema.  
> **Consiglio**: utilizzare la documentazione ufficiale di MongoDB per installare il pacchetto.

<details>
<summary>Soluzione</summary>

```bash
sudo apt install -y gnupg curl
curl -fsSL https://www.mongodb.org/static/pgp/server-8.0.asc | \
   sudo gpg -o /usr/share/keyrings/mongodb-server-8.0.gpg \
   --dearmor
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-8.0.gpg ] https://repo.mongodb.org/apt/ubuntu noble/mongodb-org/8.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-8.0.list
sudo apt update
sudo apt install -y mongodb-org
```
</details>

