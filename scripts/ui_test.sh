# this script assumes that host machine and connected physical device are on the same network
# else this would not work!
# getting ip address logic is broken and currently needs to be hardcoded

# helpers
# # get IP address using 'ip' command
# get_ip_ip_cmd() {
#     ip addr show | grep 'inet ' | grep -v 127.0.0.1 | awk '{print $2}' | cut -d/ -f1
# }

# # get IP address using 'ifconfig' command
# get_ip_ifconfig_cmd() {
#     # impl is broken right now
#     ifconfig | grep 'inet ' | grep -v 127.0.0.1 | awk '{print $2}'
# }

get_ip() {
    # # Check if 'ip' command is available
    # if command -v ifconfig > /dev/null 2>&1; then
    #     LOCAL_IP_ADDRESS=192.168.0.107
    # # ifconfig is deprecated and has been replaced by the ip command in most modern Linux distributions
    # elif command -v ip > /dev/null 2>&1; then
    #     LOCAL_IP_ADDRESS=$(get_ip_ip_cmd)
    # else
    #     echo "Error: Neither 'ip' nor 'ifconfig' command is available."
    #     exit 1
    # fi
    LOCAL_IP_ADDRESS=192.168.0.107
}

get_ip

DEFAULT_WIREMOCK_SERVER=8080
MOCK_SERVER_URL="http://$LOCAL_IP_ADDRESS:$DEFAULT_WIREMOCK_SERVER"
echo "$MOCK_SERVER_URL"

# start wiremock server in background (&)
(cd wiremock && java -jar wiremock-standalone.jar &)

maestro test .maestro/flows/quantity_selector.yaml -e MOCK_SERVER_URL=$MOCK_SERVER_URL

# reset the mock-server
curl \
    -X POST \
    -L "$MOCK_SERVER_URL/__admin/mappings/reset" \
    -d '{}'

# kill the wiremock server
curl \
    -X POST \
    -L "$MOCK_SERVER_URL/__admin/shutdown"