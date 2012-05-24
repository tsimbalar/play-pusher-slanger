# Play 2.0 demo application using WebSockets via Pusher...


It uses stuff from :

* https://gist.github.com/1129391
* https://github.com/stevegraham/slanger


## Getting it to work on OSX Lion

Before I forget, to get Slanger working on my MacBook Pro with OSX Lion and be able to work locally :

* XCode latest version + command line tools
* Homebrew to easily get wget :p

### Installing Redis
Install Redis witch is necessary for Slanger (see http://redis.io/download ) :

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

### Installing Ruby (if you don't have it already)
Be sure to have ruby installed, with version 1.9.2-p290 or greater ... I didn't so I installedit via rvm, to avoid conflicts with the System version of Ruby :

1. install rvm

```  
$ curl -L get.rvm.io | bash -s stable
```
2. ... and do what they ask to do (source ~/.rvm/scripts/...)

3. install the latest version of ruby and make it the default version 

```
rvm get head
rvm install 1.9.3
rvm use 1.9.3
rvm --default 1.9.3
```

### Installing Slanger
As simple as : 


```
gem install slanger
```

## Launchin all the bazar
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


### Troubleshooting
I found those links useful when setting up my laptop:

* http://eddorre.com/posts/rails-ultimate-install-guide-on-os-x-lion-using-rvm-homebrew-and-pow
* https://rvm.io/rvm/install/
* http://pragmaticstudio.com/blog/2010/9/23/install-rails-ruby-mac