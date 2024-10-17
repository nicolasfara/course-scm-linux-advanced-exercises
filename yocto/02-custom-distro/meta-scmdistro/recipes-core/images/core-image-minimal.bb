SUMMARY = "Minimal SCM custom Image"
DESCRIPTION = "A minimal image built without poky"
LICENSE = "MIT"

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
    "

IMAGE_LINGUAS = " "
IMAGE_FSTYPES ?= "tar.bz2"