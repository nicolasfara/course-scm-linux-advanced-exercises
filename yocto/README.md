# Esercitazione Yocto

## Setup ambiente di lavoro

Assicurarsi di avere i seguenti requisti installati:

- Almeno 90GB di spazio libero su disco
- Docker
- Git

Assicurarsi di aver inizializzato tutti i submodules:

```bash
git submodule update --init --recursive
```

Per configurare l'ambiente di lavoro:

```bash
docker run --rm -it -v .:/workdir crops/poky:ubuntu-22.04 --workdir=/workdir
```

Questo comando scaricherà l'immagine `crops/poky:ubuntu-22.04` e monterà la cartella corrente all'interno del container nella cartella `/workdir`.

Questo container è pensato appositamente per lavorare con Yocto e Bitbake.
Contiene infatti tutti i tool necessari per compilare un sistema operativo embedded senza installare manualmente tutte le dipendenze sul proprio sistema.

> **Nota**: eseguire il comando all'interno della cartella `yocto`.

Se il comando è stato eseguito correttamente, il prompt del terminale dovrebbe essere cambiato come segue:

```bash
$ docker run --rm -it -v .:/workdir crops/poky:ubuntu-22.04 --workdir=/workdir                                                                                                                                              
pokyuser@ee9b9c602093:/workdir$
```

Eseguendo il comando `ls` all'interno del container, si dovrebbe vedere il contenuto della cartella corrente:

```bash
okyuser@ee9b9c602093:/workdir$ ls
poky  README.md
```

Procediamo con la creazione del nostro primo progetto Yocto.

```bash
source poky/oe-init-build-env
```

Questo comando configura una serie di variabili d'ambiente utili alla compilazione della distro
e imposta la cartella `build` come cartella di lavoro.

L'output del comando dovrebbe essere simile a questo:

```bash
pokyuser@ee9b9c602093:/workdir$ source poky/oe-init-build-env 
This is the default build configuration for the Poky reference distribution.

### Shell environment set up for builds. ###

You can now run 'bitbake <target>'

Common targets are:
    core-image-minimal
    core-image-full-cmdline
    core-image-sato
    core-image-weston
    meta-toolchain
    meta-ide-support

You can also run generated qemu images with a command like 'runqemu qemux86-64'.

Other commonly useful commands are:
 - 'devtool' and 'recipetool' handle common recipe tasks
 - 'bitbake-layers' handles common layer tasks
 - 'oe-pkgdata-util' handles common target package tasks
```

## 01 - Immagine base

> Esercizio 01: Creare un'immagine base di Yocto sulla base dei seguenti requisiti:
>
> - La distribuzione deve supportare processori ARM
> - La distribuzione **NON** deve essere quella di default (`poky`)
> - La distribuzione deve avere supporto per il package manager `opkg`
