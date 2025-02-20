# Build Spring Boot project by CLI

- [Installing the CLI :: Spring Boot](https://docs.spring.io/spring-boot/cli/installation.html)
- [Using the CLI :: Spring Boot](https://docs.spring.io/spring-boot/cli/using-the-cli.html)
- https://start.spring.io/

Building by CLI  Spring Boot projects is easy but your must be careful with no using a outdated versoin of Spring Boot CLI.

To resolve this, you need to properly install the Spring Boot CLI, uninstalling the current/previous Spring CLI installation.

If you do not have any previous or current installation go to step 3. 

Here's how to do it:

1. First, uninstall the current Spring CLI installation:

```bash
sudo apt remove spring
```

2. Install SDKMAN, which is a better tool for managing Spring Boot CLI:

```bash
curl -s "https://get.sdkman.io" | bash source "$HOME/.sdkman/bin/sdkman-init.sh"
```

3. Use <mark>SDKMAN</mark> to install the latest <mark>Spring Boot CLI</mark>:

```bash
sdk install springboot`
```

4. Verify the installation:

```bash
spring --version
```

This should now show the latest version of Spring Boot CLI.

5. Now, run `spring init`command:

```bash
spring init \
    --dependencies=web,data-jpa,h2,devtools,lombok \
    --build=maven \
    --java-version=21 \
    --packaging=jar \
    --version=3.4.2 \
    --name=borrowabook \
    borrowabook
```

> If you still encounter issues, ensure that Java 21 is installed on your system, as Spring Boot 3.4.2 requires Java 17 or later. You can install Java 21 using:

```bash
sudo apt install openjdk-21-jdk
```

After installing Java 21, set it as the default Java version:

```bash
sudo update-alternatives --config java
```

Choose the option corresponding to Java 21.

### Citations:

1. [Spring Boot CLI Setup and HelloWorld Example | DigitalOcean](https://www.digitalocean.com/community/tutorials/spring-boot-cli-setup-and-helloworld-example)
2. [How to install spring boot CLI on Mac? - Stack Overflow](https://stackoverflow.com/questions/47704002/how-to-install-spring-boot-cli-on-mac)
3. [Spring Cloud CLI installation with Spring Boot 1.4 CLI - ClassNotFoundException - org.springframework.boot.cli.command.CommandFactory - Stack Overflow](https://stackoverflow.com/questions/38862408/spring-cloud-cli-installation-with-spring-boot-1-4-cli-classnotfoundexception)
4. [Installing the CLI :: Spring Boot](https://docs.spring.io/spring-boot/cli/installation.html)
5. [Install instructions do not work with spring-boot-cli 3.0.0 · Issue #180 · spring-cloud/spring-cloud-cli · GitHub](https://github.com/spring-cloud/spring-cloud-cli/issues/180)
6. [Spring Boot CLI :: Spring Boot](https://docs.spring.io/spring-boot/cli/index.html)
7. [Failed to run spring cloud cli · Issue #80 · spring-cloud/spring-cloud-cli · GitHub](https://github.com/spring-cloud/spring-cloud-cli/issues/80)
8. [Reddit - Dive into anything](https://www.reddit.com/r/SpringBoot/comments/1fm7ik0/install_springboot_cli_with_homebrew_doesnt/)
