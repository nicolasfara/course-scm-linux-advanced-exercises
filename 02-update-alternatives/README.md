# 02-update-alternatives

## Esercizio 01

> Controllare quali "alternative" sono disponibili per il gruppo `editor`.

**Tempo stimato**: 2 minuti

<details>
<summary>Soluzione</summary>

```bash
update-alternatives --display editor
```

</details>

## Esercizio 02

> Impostare `vim` come editor di default per il gruppo `editor`.

**Tempo stimato**: 2 minuti

<details>
<summary>Soluzione</summary>

```bash
update-alternatives --set editor /usr/bin/vim.basic
```

</details>

## Esercizio 03

> Installare nel proprio sistema `java` nelle versioni 11 e 21.
> Utilizzare `update-alternatives` per impostare la versione 11 come predefinita.
> Verificare con il comando `java -version` che la versione 11 sia stata impostata correttamente.

**Tempo stimato**: 5 minuti

<details>
<summary>Soluzione</summary>

```bash
sudo apt install openjdk-11-jdk openjdk-21-jdk
sudo update-alternatives --config java
java -version
```

</details>
