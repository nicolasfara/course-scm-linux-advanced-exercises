# Esercitazione Yocto

## Setup ambiente di lavoro

Assicurarsi di avere i seguenti requisti installati:

- Almeno 90GB di spazio libero su disco
- Docker
- Git

Assicurarsi di aver inizializzato tutti i submodules:

```bash
git submodule update --init --recursive
cd poky
git checkout scarthgap-5.0.5
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
> - La distribuzione deve essere minimale

**Tempo stimato**: 15 minuti

### Soluzione

<!--
<details>
<summary>Clicca qui per la soluzione</summary>

Nel file `build/conf/local.conf` aggiungere le seguenti righe:

```bash
MACHINE ?= "genericarm64" # Supporto per processori ARM
DISTRO ?= "poky-tiny" # Distribuzione alternativa a quella di default, più leggera
PACKAGE_CLASSES ?= "package_ipk" # Supporto per il package manager opkg
```

Compilare l'immagine:

```bash
bitbake core-image-minimal
```

</details>
-->

## 02 - Aggiungere supporto per Raspberry Pi

> Esercizio 02: Aggiungere il supporto per Raspberry Pi al progetto Yocto.
>
> Aggiungere supporto per Raspberry Pi 4 (versione a 64bit) al progetto Yocto.  
> **Suggerimento**: cercare il layer `meta-raspberrypi` su [OpenEmbedded Layer Index](https://layers.openembedded.org/layerindex/branch/master/layers/)

**Tempo stimato**: 20 minuti

<!--
<details>
<summary>Clicca qui per la soluzione</summary>

Aggiungere il layer `meta-raspberrypi` come submodule all'interno della cartella `yocto`:

```bash
cd poky
submodule add -b scarthgap git://git.yoctoproject.org/meta-raspberrypi
```

Aggiungere il layer al progetto:

```bash
bitbake-layers add-layer ../meta-raspberrypi
```

Oppure editare il file `build/conf/bblayers.conf` e aggiungere il layer manualmente:

```bash
BBLAYERS ?= " \
  ${TOPDIR}/../poky/meta \
  ${TOPDIR}/../poky/meta-poky \
  ${TOPDIR}/../poky/meta-yocto-bsp \
  ${TOPDIR}/../meta-raspberrypi \
  "
```

A questo punto se si compilasse l'immagine per Raspberry Pi 4, si otterrebbe un errore di compilazione:

```
ERROR: Nothing RPROVIDES 'linux-firmware-rpidistro-bcm43456' (but /workdir/build/../poky/meta/recipes-core/packagegroups/packagegroup-base.bb RDEPENDS on or otherwise requires it)
linux-firmware-rpidistro RPROVIDES linux-firmware-rpidistro-bcm43456 but was skipped: Has a restricted license 'synaptics-killswitch' which is not listed in your LICENSE_FLAGS_ACCEPTED.
```

Per risolvere questo problema, è necessario modificare il file `build/conf/local.conf` e aggiungere la seguente riga:

```bash
LICENSE_FLAGS_WHITELIST = "synaptics-killswitch"
```

A questo punto è possibile compilare l'immagine per Raspberry Pi 4:

```bash
bitbake core-image-base
```

</details>
-->

## 03 - Installazione utility per interagire con Raspberry Pi

> Esercizio 03: Installare le utility necessarie per interagire con Raspberry Pi.
>
> Installare nell'immagine l'utility `rpi-gpio` e `raspi-gpio` per interagire con i GPIO di Raspberry Pi.
> **Suggerimento**: cercare i pacchetti all'interno del layer `meta-raspberrypi` installato precedentemente.

**Tempo stimato**: 10 minuti

<!--
<details>
<summary>Clicca qui per la soluzione</summary>

Aggiungere i pacchetti `rpi-gpio` e `raspi-gpio` al file `build/conf/local.conf`:

```bash
IMAGE_INSTALL:append = " rpi-gpio raspi-gpio"
```

Compilare l'immagine:

```bash
bitbake core-image-base
```

</details>
-->