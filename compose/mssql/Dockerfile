FROM mcr.microsoft.com/mssql/server:2022-latest

USER root

# Create a config directory
RUN mkdir -p /usr/config
WORKDIR /usr/config


# Install the necessary tools for SQL Server
RUN apt-get update && \
    apt-get install -y curl apt-transport-https gnupg && \
    curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - && \
    curl https://packages.microsoft.com/config/ubuntu/$(lsb_release -rs)/prod.list > /etc/apt/sources.list.d/mssql-release.list && \
    apt-get update && \
    ACCEPT_EULA=Y apt-get install -y msodbcsql17 mssql-tools unixodbc-dev && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Bundle config source
COPY . /usr/config

# Grant permissions for to our scripts to be executable
RUN chmod +x /usr/config/entrypoint.sh
RUN chmod +x /usr/config/configure-db.sh

USER mssql

ENTRYPOINT ["/usr/config/entrypoint.sh"]