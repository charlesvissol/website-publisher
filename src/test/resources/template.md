

<section>

# Title 1

## Title 2

### Title 3

</section>

<br>

<section>

# Paragraph & lists

If you use a command of the shell itself (a shell "builtin") no further search is required, but if you need another tool, you need to locate it. In this context `$PATH` environment helps you because it holds a list of places where the shell searches and the order in which it searches.

- Use pandoc to convert markdown into PDF use pdftext (Latex PDF converter): `sudo apt-get install texlive-latex-base sudo apt-get install texlive-bibtex-extra sudo apt-get install texlive-latex-extra sudo apt-get install texlive-fonts-extra sudo apt-get install texlive-science` `apt-get install wkhtmltopdf` -> for pandoc markdown to pdf `apt install librsvg2-bin` -> convert svg into pdftext

- Use pandoc to convert markdown into PDF use pdftext (Latex PDF converter): `sudo apt-get install texlive-latex-base sudo apt-get install texlive-bibtex-extra sudo apt-get install texlive-latex-extra sudo apt-get install texlive-fonts-extra sudo apt-get install texlive-science` `apt-get install wkhtmltopdf` -> for pandoc markdown to pdf `apt install librsvg2-bin` -> convert svg into pdftext

- Use pandoc to convert markdown into PDF use pdftext (Latex PDF converter): `sudo apt-get install texlive-latex-base sudo apt-get install texlive-bibtex-extra sudo apt-get install texlive-latex-extra sudo apt-get install texlive-fonts-extra sudo apt-get install texlive-science` `apt-get install wkhtmltopdf` -> for pandoc markdown to pdf `apt install librsvg2-bin` -> convert svg into pdftext


</section>


<section>

# Notes 

> **Important:**
> 
> This project in [https://whoamiproject.tech](https://whoamiproject.tech) provides anhanced privacy, anonymity packages for Debian and Arch based linux distributions


</section>


<section>

# Code



```bash
pandoc --pdf-engine=xelatex --highlight-style=breezedark Debian_11_secure_
``` 

If you want to know if a command is a shell builtin, tip `type <command>`

```bash
vissol@debian-dev:~$ type clear
clear is /usr/bin/clear
vissol@debian-dev:~$ type cd
cd is a shell builtin
```



</section>


<section>

# Table


| *Move*                | *In a Line*                 |
| --------------------- | --------------------------- |
| `Ctrl` + `>`          | Move Cursor forward a word  |
| `Ctrl` + `<`          | Move Cursor backward a word |
| `Ctrl` + `a`          | Move Cursor to start line   |
| `Ctrl` + `e`          | Move Cursor to end line     |
| ***Move***            | ***in a Terminal***         |
| `Shift` + `Up`        | Move to line Up<br/>        |
| `Shift` + `Page Up`   | Move to page Up             |
| `Shift` + `Down`      | Move to line Down           |
| `Shift` + `Page Down` | Move to page Down           |




</section>


<section>

# Image

![](/home/vissol/eclipse-workspace/angrybeewebsite/src/main/website/articles/pictures/cgroups/docker_archi.png)