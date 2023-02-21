# First title

A huge advantage that Unix and Linux operating systems have over Windows is that Unix and Linux do a much better job of keeping privileged administrative accounts separated from normal user accounts. 
Indeed, one reason that older versions of Windows were so susceptible to security issues, such as drive-by virus `infections`, was the common practice of setting up user accounts with administrative privileges, without having the protection of the **User Access Control** (**UAC**) that's in newer versions of Windows. (Even with UAC, Windows systems still do get infected, just not quite as often.) With `Unix` and `Linux`, it's a lot harder to infect a properly configured system.

```bash
cat /boot/config-5.10.0-16-amd64 | grep CGROUP
CONFIG_CGROUPS=y
CONFIG_BLK_CGROUP=y
CONFIG_CGROUP_WRITEBACK=y
CONFIG_CGROUP_SCHED=y
CONFIG_CGROUP_PIDS=y
CONFIG_CGROUP_RDMA=y
CONFIG_CGROUP_FREEZER=y
CONFIG_CGROUP_HUGETLB=y
CONFIG_CGROUP_DEVICE=y
CONFIG_CGROUP_CPUACCT=y
CONFIG_CGROUP_PERF=y
CONFIG_CGROUP_BPF=y
# CONFIG_CGROUP_DEBUG is not set
CONFIG_SOCK_CGROUP_DATA=y
CONFIG_BLK_CGROUP_RWSTAT=y
# CONFIG_BLK_CGROUP_IOLATENCY is not set
CONFIG_BLK_CGROUP_IOCOST=y
# CONFIG_BFQ_CGROUP_DEBUG is not set
CONFIG_NETFILTER_XT_MATCH_CGROUP=m
CONFIG_NET_CLS_CGROUP=m
CONFIG_CGROUP_NET_PRIO=y
CONFIG_CGROUP_NET_CLASSID=y
```

![](/home/vissol/softs/dev-projects/website-publisher/src/test/resources/test1.svg)

A huge advantage that Unix and Linux operating systems have over Windows is that Unix and Linux do a much better job of keeping privileged administrative accounts separated from normal user accounts. 
Indeed, one reason that older versions of Windows were so susceptible to security issues, such as drive-by virus infections, was the common practice of setting up user accounts with administrative privileges, without having the protection of the **User Access Control** (**UAC**) that's in newer versions of Windows. (Even with UAC, Windows systems still do get infected, just not quite as often.) With Unix and Linux, it's a lot harder to infect a properly configured system.

A huge advantage that Unix and Linux operating systems have over Windows is that Unix and Linux do a much better job of keeping privileged administrative accounts separated from normal user accounts. 
Indeed, one reason that older versions of Windows were so susceptible to security issues, such as drive-by virus infections, was the common practice of setting up user accounts with administrative privileges, without having the protection of the **User Access Control** (**UAC**) that's in newer versions of Windows. (Even with UAC, Windows systems still do get infected, just not quite as often.) With Unix and Linux, it's a lot harder to infect a properly configured system.

## header 2

| *Cut/Copy/Paste*           | -                            |
| -------------------------- | ---------------------------- |
| `Ctrl` + `k`               | Cut everything after Cursor  |
| `Ctrl` + `u`               | Cut everything before Cursor |
| `Ctrl` + `shift` + `c`/`v` | Copy/Paste into terminal     |
| `Ctrl` + `y`               | Paste from buffer            |

Complete Debian installation of Nextcloud & Spreedme

Source: [Complete NC installation on Debian with Spreed.me and TURN step by step - 📑 How to - Nextcloud community](https://help.nextcloud.com/t/complete-nc-installation-on-debian-with-spreed-me-and-turn-step-by-step/2436)

Because i’m testing with [Spreed.me 106](http://Spreed.me) and TURN server i made a installation manual. Maybe any of you could use this document. This document is a step by step manual for installing Nextcloud on a fresh installed Debian 8.5 server with only ssh-server installed and standard system tools (i like to keep it clean). If you follow the document step by step you will have a complete installed nextcloud server with a working [Spreed.me 106](http://Spreed.me) app and a start with TURN server. I use vim as editor but you’re offcourse welcome to use your own flafour.

Install packages for apache, mariadb, php, nextcloud and enable ssl

```bash
apt-get install vim
apt-get install unzip
apt-get install sudo
```

Installing apache2.4 and Mariadb

```bash
apt-get install apache2 mariadb-server libapache2-mod-php5
```

Installing php modules

```bash
apt-get install php5-gd php5-json php5-mysql php5-curl apt-get install php5-intl php5-mcrypt php5-imagick
```

Download unzip and move latest NextCloud

```bash
wget https://download.nextcloud.com/server/releases/nextcloud-12.0.0.zip unzip nextcloud-12.0.0.zip mv nextcloud/ /var/www
```

Enable SSL

```bash
a2enmod ssl a2ensite default-ssl
```

Now for some reason the default-ssl prevents [spreed.me 27](http://spreed.me) from starting. So create your own certificate:

```bash
mkdir /etc/apache2/ssl cd /etc/apache2/ssl openssl req -new  -x509 -days 365 -nodes -out /etc/apache2/ssl/apache.pem -keyout  /etc/apache2/ssl/apache.key Just enter trough all the certificate  questions chmod 600 /etc/apache2/ssl/apache.*
```

Create vhost file (rename host.domain.nl to your own host.domain):

Download the virtualhost file here: [https://cloud.i-erik.nl/s/ZpQsqWsTWGfmgnR/download 17](https://cloud.i-erik.nl/s/ZpQsqWsTWGfmgnR/download) I don’t know how to paste it here without messing it up. If anybody does know let me know so i can add it for eternity.

Create symbolic link to sites-enabled

```bash
ln -s /etc/apache2/sites-available/nextcloud.conf /etc/apache2/sites-enabled/nextcloud.conf
```

Enable apache modules:

```bash
a2enmod rewrite a2enmod headers a2enmod env a2enmod dir a2enmod  mime a2enmod ssl a2ensite default-ssl a2enmod proxy proxy_http  proxy_wstunnel
```

If you’re running mod_fcgi instead of the standard mod_php also enable:

```bash
a2enmod setenvif
```

Set the right permissions on the files in the nextcloud folder. For now we do it the easy way, later on we will use the strong permissions script for nextcloud

```bash
cd /var/www/nextcloud
chown www-data:www-data . -R
```

Installing NextCloud

```bash
cd /var/www/nextcloud sudo -u www-data php occ maintenance:install --database "mysql" --database-name "nextcloud" --database-user "root"  --database-pass "<password>" --admin-user "ncadmin" --admin-pass  "password"
```

Make sure you have a dns record or configured host file so you can access your virtualhost name based. Open your browser and go to [http://host.domain.nl 44](http://host.domain.nl) (change to your host and domain)

You probably get an error:

```bash
You are accessing the server from an untrusted domain. Please  contact your administrator. If you are an administrator of this  instance, configure the "trusted_domains" setting in config/config.php.  An example configuration is provided in config/config.sample.php.      Depending on your configuration, as an administrator you might also be  able to use the button below to trust this domain.
```

Just open your `/var/www/nextcloud/config/config.php` and add:

`vi /var/www/nextcloud/config/config.php` find the line with 0 => ‘localhost’, and add a line below like 1 => ‘host.domain.nl’,

```php
array ( 0 => 'localhost', 1 => 'host.domain.nl',
```

I also move the data folder from `/var/www/nextcloud/data` to `/var/oc_data`

```bash
mv /var/www/nextcloud/data /var/oc_data
```

And change the data folder in `/var/www/nextcloud/config/config.php`

```php
'datadirectory' => '/var/oc_data',
```

If you have done this you can also run the next script for strong file permissions:

```bash
vi /var/www/oc_perm.sh
####### Copy and Paste from #!/bin/bash to the last fi`
 `#!/bin/bash ncpath='/var/www/nextcloud' ncdata='/var/oc_data' htuser='www-data' htgroup='www-data' rootuser='root'
printf "Creating possible missing Directories\n" mkdir -p $ncpath/data mkdir -p $ncpath/assets mkdir -p $ncpath/updater
printf "chmod Files and Directories\n" find ${ncpath} -type f  -print0 | xargs -0 chmod 0640 find ${ncpath} -type d -print0 | xargs -0  chmod 0750 find ${ncdata} -type f -print0 | xargs -0 chmod 0640 find  ${ncdata} -type d -print0 | xargs -0 chmod 0750
printf "chown Directories\n" chown -R ${rootuser}:${htgroup}  ${ncpath} chown -R ${htuser}:${htgroup} ${ncpath}/apps/ chown -R  ${htuser}:${htgroup} ${ncpath}/assets/ chown -R ${htuser}:${htgroup}  ${ncpath}/config/ chown -R ${htuser}:${htgroup} ${ncdata}/ chown -R  ${htuser}:${htgroup} ${ncpath}/themes/ chown -R ${htuser}:${htgroup}  ${ncpath}/updater/
chmod +x ${ncpath}/occ
printf "chmod/chown .htaccess\n"`
 if [ -f ${ncpath}/.htaccess ]
 `then chmod 0644 ${ncpath}/.htaccess`
 `chown ${rootuser}:${htgroup} ${ncpath}/.htaccess`
 `fi if [ -f ${ncdata}/.htaccess ]`
 `then chmod 0644 ${ncdata}/.htaccess chown ${rootuser}:${htgroup} ${ncdata}/.htaccess fi`
 `######### END SCRIPT#########
```

That concludes the basic Nextcloud installation and configuration on a Debian 8.5 server. Now for the WebRTC fun stuff. You can now login in nextcloud by going to [https://host.domain.nl](https://host.domain.nl)

First we gonna make go available on our system. Don’t install it with apt because you’ll get a 1.3 version wich is to low. Install it from source, its verry easy:

First download go

```bash
cd /root wget  https://storage.googleapis.com/golang/go1.7.linux-amd64.tar.gz tar xzvf  go1.7.linux-amd64.tar.gz mv go/ /usr/local vi /root/.profile
```

add after fi and before mesg n

```bash
export PATH=$PATH:/usr/local/go/bin
```

I also run this command straight from the command line to make available instantly

```bash
export PATH=$PATH:/usr/local/go/bin
```

Now test if go can be found and is working

```bash
go version go version go1.7 linux/amd64
```

Now install WebRTC

First we need some more packages. Git and node.js

```bash
apt-get install git node.js make automake
cd /opt wget https://github.com/strukturag/spreed-webrtc/archive/master.zip unzip master.zip rm master.zip
cd spreed-webrtc-master ./autogen.sh ./configure make
```

If all finished without errors, then kuddo’s, you really followed this manual. Now you can now configure webrtc. We still need to be in /opt/spreed-webrtc-master First copy the de default config file server.conf.in to server.conf

```bash
cp server.conf.in server.conf
```

Lets first generate a secret for our sessionSecret

```bash
openssl rand -hex 32 1e719578d2345d32f7ce467d891111f1ba6aa8bexxxxxxxxxxxxxxxx
```

Copy this string to your memory so you can paste it in the next config file

You need to adjust te following lines to be exactly like (except for the sessionSecret and sharedsecret_secret ofcourse):

```bash
vi server.conf
[http]
root = /opt/spreed-webrtc-master
listen = 127.0.0.1:8080
basePath = /webrtc/

[app]
sessionSecret = 02819e83254f793608a1a6b1adb11ed657dxxxxxxxxxxxxxxxxxxxxxxxxxxx
encryptionSecret = c3ec0dc5ead5a8c95bcbae94fcde149xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
authorizeRoomJoin = true
serverToken = 9b88f0aee6fde09a749e65d061de84xxxxxxxxxxxxxxxxxxxxx
serverRealm = mynextcloud.net
extra = /var/www/nextcloud/apps/spreedme/extra
plugin = extra/static/owncloud.js

turnURIs = turn:mynextcloud.net:8443?transport=udp turn:mynextcloud.net:8443?transport=tcp
turnSecret = 6cf6cbc540e9667f3552773df92edbd442eca209axxxxxxxxxxxxxxxxxxxxxx

[users]
enabled = true
mode = sharedsecret
sharedsecret_secret = 10b774ef3db23e63a4d80c69b7879193xxxxxxxxxxxxxxxxxxxx
```

Save and close the file. This concludes the configuration of spreed-webrtc.

Now we need the [Spreed.me 106](http://Spreed.me) nextcloud app

```bash
cd /var/www/nextcloud/apps wget  https://github.com/strukturag/nextcloud-spreedme/archive/master.zip  unzip master.zip mv nextcloud-spreedme-master spreedme cd  spreedme/config cp config.php.in config.php vi config.php
```

Add your sharedSecret from ealier to the config

```bash
OWNCLOUD_TEMPORARY_PASSWORD_LOGIN_ENABLED = true  SPREED_WEBRTC_SHAREDSECRET =  ‘1e719578d2345d32f7ce467d891111f1ba6aa8bexxxxxxxxxxxxxxxx’
```

Thats it. Save and close the file. This concludes the configuration of [spreed.me 27](http://spreed.me) app.

```bash
cd ../extra/static/config cp OwnCloudConfig.js.in OwnCloudConfig.js
```

Now we can start [spreed.me 27](http://spreed.me)

```bash
cd /opt/spreed-webrtc-master/`
 `./spreed-webrtc-server
```

This command makes spreed run in the forground. Use the next command to run in background (at least until your next boot. U can ofcourse make a init script. Please leave samples below i’m not that good in init scripts)

```bash
nohup ./spreed-webrtc-server > /dev/null 2>&1 &
```

Check if it is running

```bash
ps -e |grep spreed
```

Now the [Spreed.me 106](http://Spreed.me) app is installed and configured. Login to Nextcloud, open the apps page. Select Not Enabled and scroll to the bottom and enable the [Spreed.me 106](http://Spreed.me) app

Just remember. Your spreedme cam sessions will only work if you and the one you call are in the same network, or are directly connected to the internet. When you are inside a company network your peer to peer traffic will most likely be blocked by the firewall. There is lots of UDP traffic for the webcam data. I’m now trying to configure coturn (TURN server) to port all udp traffic on random ports (49152:65535) to port 443, but i dont know exactly how it works yet. And if you get it to work it might still be blocked by firewalls that do data fingerprint scanning But to get you started do the following :

```bash
apt-get install coturn vi /etc/turnserver.conf
```

This is my config so far just change the secrets:

```
listening-port=8443 alt-listening-port=3478 fingerprint  lt-cred-mech use-auth-secret  static-auth-secret=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  realm=MyDnsService total-quota=100 bps-capacity=0 stale-nonce  cipher-list="ECDH+AESGCM:DH+AESGCM:ECDH+AES256:DH+AES256:ECDH+AES128:DH+AES:ECDH+3DES:DH+3DES:RSA+AES:RSA+3DES:!ADH:!AECDH:!MD5" log-file=/var/log/spreed/turn/turn.log no-loopback-peers  no-multicast-peers cert=/etc/letsencrypt/XXX/cert.pem  pkey=/etc/letsencrypt/XXX/privkey.pem
```

And now enable TURN as a service and restart coturn

```bash
vi /etc/default/coturn Remove # in front of TURNSERVER_ENABLED=1 /etc/init.d/coturn restart
```
