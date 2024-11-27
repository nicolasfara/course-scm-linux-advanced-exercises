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

## 02 - Aggiungere supporto per Raspberry Pi

> Esercizio 02: Aggiungere il supporto per Raspberry Pi al progetto Yocto.
>
> Aggiungere supporto per Raspberry Pi 4 (versione a 64bit) al progetto Yocto.  
> **Suggerimento**: cercare il layer `meta-raspberrypi` su [OpenEmbedded Layer Index](https://layers.openembedded.org/layerindex/branch/master/layers/)

**Tempo stimato**: 20 minuti

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

## 03 - Installazione utility per interagire con Raspberry Pi

> Esercizio 03: Installare le utility necessarie per interagire con Raspberry Pi.
>
> Installare nell'immagine l'utility `rpi-gpio` e `raspi-gpio` per interagire con i GPIO di Raspberry Pi.
> **Suggerimento**: cercare i pacchetti all'interno del layer `meta-raspberrypi` installato precedentemente.

**Tempo stimato**: 10 minuti

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

## 04 - Debloat dell'immagine e accesso ssh

> Esercizio 04: Rimuovere i pacchetti non necessari dall'immagine e abilitare l'accesso ssh.
>
> Operare sulla variabile `IMAGE_INSTALL` per rimuovere i pacchetti non necessari dall'immagine.
> Abilitare l'accesso ssh.
>
> **Tip**: Il comand `bitbake-getvar <VARIABILE>` può essere utile per ottenere il valore di una variabile BitBake.

**Tempo stimato**: 10 minuti

<!--
<details>
<summary>Clicca qui per la soluzione</summary>

Per ottenere la lista dei pacchetti installati nell'immagine, eseguire il comando:

```bash
$ bitbake-getvar IMAGE_INSTALL
```

Modificare il file `build/conf/local.conf` per rimuovere i pacchetti non necessari e abilitare l'accesso ssh:

```bash
EXTRA_IMAGE_FEATURES:append = " ssh-server-openssh"
DISTRO_FEATURES:remove = " nfc x11 opengl wayland vulkan"
```

Compilare l'immagine:

```bash
bitbake core-image-base
```

</details>
-->

## 05 - Abilitare Systemd come init system

> Esercizio 05: Abilitare Systemd come init system.
>
> **Tip**: consultare la documentazione ufficiale al link: https://docs.yoctoproject.org/5.0.5/dev-manual/init-manager.html

**Tempo stimato**: 5 minuti

<!--
<details>
<summary>Clicca qui per la soluzione</summary>

Modificare il file `build/conf/local.conf` per abilitare Systemd come init system:

```bash
DISTRO_FEATURES:append = " systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
```

Compilare l'immagine:

```bash
bitbake core-image-base
```

</details>
-->

## 04 - Creare layer per gestire servizio web

> Esercizio 04: Creare un layer custom `meta-scmservice` affinché installi il servizio web fornito di seguito.
>
> Predisporre un layer custom con nome `meta-scmservice` e apportare le necessarie modifiche al file `bblayers.conf` affinché il layer sia riconosciuto da BitBake.  
> Il layer deve contenere **una** ricetta (`recipes-scmservice`) per l'installazione nel sistema dello script `scmservice.py`.  
> Infine, predisporre un **service** di _systemd_ per avviare il servizio al boot.
>
> **Tip**: potrebbe essere utile leggere la documentazione in testa al file `systemd.bbclass` presente in `poky/meta/classes-recipe` per configurare il servizio all'avvio.

Il seguente file (`scmservice.py`) deve far parte dei file della ricetta richiesta dall'esercizio.

```python
#!/usr/bin/env python3

from http.server import BaseHTTPRequestHandler, HTTPServer
import platform

hostName = "0.0.0.0"
serverPort = 8080

class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        self.wfile.write(bytes("<html><head><title>https://pythonbasics.org</title></head>", "utf-8"))
        self.wfile.write(bytes("<p>Request: %s</p>" % self.path, "utf-8"))
        self.wfile.write(bytes("<body>", "utf-8"))
        self.wfile.write(bytes("<h1>SCM & Yocto Linux</h1>", "utf-8"))
        self.wfile.write(bytes(f"<p>This is an example web server running on {platform.uname()[1]}.</p>", "utf-8"))
        self.wfile.write(bytes("</body></html>", "utf-8"))

if __name__ == "__main__":        
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")
```

<details>
<summary>Servizio Systemd per avvio al boot</summary>

Anche questo file (`scmservice.service`) deve far parte dei file della ricetta.

```systemd
[Unit]
Description=SCM Example Service
After=network.target

[Service]
ExecStart=/bin/scmservice
Restart=always

[Install]
WantedBy=multi-user.target
```
</details>

<details>
<summary>Clicca qui per la soluzione</summary>

```bash
$ bitbake-layers create-layer ../meta-scmservice
```

Aggiungere il layer al progetto:

```bash
BBLAYERS ?= " \
  ${TOPDIR}/../poky/meta \
  ${TOPDIR}/../poky/meta-poky \
  ${TOPDIR}/../poky/meta-yocto-bsp \
  ${TOPDIR}/../meta-raspberrypi \
  ${TOPDIR}/../meta-scmservice \
  "
```

Creare la cartella `recipes-webservice` all'interno del layer `meta-scmservice` con all'interno i seguenti file:

- Cartella `files` con all'interno i file `scmservice.py` e `scmservice.service`
- File `scmservice_1.0.0.bb` con il seguente contenuto:

```bash
SUMMARY = "My Python Script"
DESCRIPTION = "A simple Python script example."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://scmservice.py \
           file://scmservice.service"

S = "${WORKDIR}"

RDEPENDS:${PN} = "python3-modules"

inherit python3native
inherit systemd

do_install(){
    install -d ${D}${bindir}
    install -d ${D}${systemd_unitdir}/system

    install -m 0755 ${S}/scmservice.py ${D}${bindir}/scmservice
    install -m 0644 ${S}/scmservice.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_SERVICE:${PN} = "scmservice.service"
```

Aggiungere la feature alla configurazione:

```bash
IMAGE_INSTALL:append = " scmservice"
```

Compilare l'immagine:

```bash
bitbake core-image-base
```

</details>
