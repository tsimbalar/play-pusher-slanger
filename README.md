Play 2.0 demo application using WebSockets via Pusher...


It uses stuff from :

* https://gist.github.com/1129391
* https://github.com/stevegraham/slanger



Before I forget, to get Slanger working on my MacBook Pro with OSX Lion and be able to work locally :

* XCode latest version + command line tools
* Homebrew to easily get wget :p
* install Redis witch is necessary for Slanger (see http://redis.io/download ) :

```
wget http://redis.googlecode.com/files/redis-2.4.14.tar.gz (other version)
tar xzf redis-2.4.14.tar.gz
cd redis-2.4.14
make
make test
    
sudo mv src/redis-server /usr/bin
sudo mv src/redis-cli /usr/bin
mkdir ~/.redis
mv redis.conf ~/.redis
```

* be sure to have ruby installed, with version 1.9.2-p290 or greater ... I didn't so I installedit via rvm, to avoid conflicts with the System version of Ruby :
  * install rvm

```  
$ curl -L get.rvm.io | bash -s stable
```
  * ... and do what they ask to do (source ~/.rvm/scripts/...)

```
rvm get head
rvm install 1.9.3
rvm use 1.9.3
rvm --default 1.9.3

gem install slanger
```

When all this is done, you can :
* launch redis with default params:

```
redis-server
```

* launch slanger passing the key and appId in params (+ more options if needed)

```
slanger --app_key mykeymouse --secret theSecret
```
* go to the folder for the Play app

```
play run
```

* navigate to http://localhost:9000

* review the configuration in application.conf if needed ...