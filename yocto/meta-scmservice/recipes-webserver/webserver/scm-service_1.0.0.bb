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
