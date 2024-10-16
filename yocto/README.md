# Esercitazione Yocto

## Setup ambiente di lavoro

### VM con VirtualBox

### VM con Qemu

Installare `qemu` e `kvm`:

```bash
sudo apt update
sudo apt install qemu qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils virt-manager
```

Scaricare l'immagine di Ubuntu 22.04 LTS [da qui](https://releases.ubuntu.com/jammy/).

Creare un virtual disk per la VM, utilizzando almeno 90GB di spazio (consigliati **120GB**):

```bash
qemu-img create -f qcow2 ubuntu.img 120G
```

Eseguire la VM con il comando:

```bash
qemu-system-x86_64 \
    -m 2048 \
    -smp $(nproc) \
    -hda ubuntu.img \
    -cdrom /path/to/ubuntu.iso \
    -boot d \
    -enable-kvm \
    -net nic \
    -net user \
    -vga virtio
```

Seguire le istruzioni a schermo per installare Ubuntu.

Per eseguire la VM in seguito, utilizzare il comando:

```bash
qemu-system-x86_64 \
    -m 12GB \
    -smp $(nproc) \
    -hda ubuntu.img \
    -enable-kvm \
    -net nic \
    -net user \
    -vga virtio
```

