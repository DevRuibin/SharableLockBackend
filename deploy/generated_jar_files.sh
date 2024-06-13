#!/bin/bash

# This script is used to generate the jar files for the project

# Function to generate a jar file from a maven project.
# Arguments:
#   $1: Project directory
#   $2: Path+Name of the jar file to create
function generate_jar_file_maven {
    local project_dir="$1"
    local jar_name="$2"
    cd "$project_dir" || exit 1
    mvn clean package -DskipTests || exit 1
    local jar_files
    jar_files=$(get_jar_files "target")
    for jar_file in $jar_files; do
        echo "Copying $jar_file to $jar_name"
        cp "$jar_file" "$jar_name"
    done
    cd - || exit 1
}

# Function to generate a jar file from a gradle project.
# Arguments:
#   $1: Project directory
#   $2: Path+Name of the jar file to create
function generate_jar_file_gradle {
    local project_dir="$1"
    local jar_name="$2"
    cd "$project_dir" || exit 1
    gradle clean build -x test || exit 1
    local jar_files
    jar_files=$(get_jar_files "build/libs")
    for jar_file in $jar_files; do
        echo "Copying $jar_file to $jar_name"
        cp "$jar_file" "$jar_name"
    done
    cd - || exit 1
}

# Function to find *.jar files in a specified directory
# Arguments:
#   $1: Directory to search for *.jar files
function get_jar_files {
    find "$1" -name "*.jar"
}

# function to generate the jar files for the project
function generate_jar_files {
  local microservices=("$@")
  for microservice in "${microservices[@]}"; do
    local project_dir="../$microservice"
    local deploy_dir
    deploy_dir=$(realpath "../deploy")
    local jar_name="$deploy_dir/jars/${microservice//\//_}.jar"
    if [ -f "$project_dir/build.gradle" ]; then
      generate_jar_file_gradle "$project_dir" "$jar_name"
    elif [ -f "$project_dir/pom.xml" ]; then
      generate_jar_file_maven "$project_dir" "$jar_name"
    else
      echo "No build file found for $microservice"
    fi
  done
}

function main {
    local microservices=()

    # Create 'jars' directory if it doesn't exist
    if [ ! -d "jars" ]; then
        mkdir jars
    fi

    # Check if specific microservices are passed as arguments
    if [ "$#" -gt 0 ]; then
        microservices=("$@")  # Use provided arguments
    else
        # Default microservices array
        declare -a default_microservices=("authentication"
            "config-server"
            "discovery"
            "gateway"
            "lock"
            "logging"
            "message"
            "mqtt-server"
            "user"
            "StaticServer/Server"
        )
        microservices=("${default_microservices[@]}")
    fi

    generate_jar_files "${microservices[@]}"
}

# Function to remove all the generated files
function clean_up {
    rm -rf jars
}

# Function to remove the specified jar file, if it exists.
# Arguments:
#   $1: Jar file to remove or microservice name. if the name wihout .jar extension is passed, the script will remove the jar file with the same name.
function remove_jar_file {
    local jar_file="$1"
    jar_file=${jar_file//\//_}
    if [[ ! $jar_file == *.jar ]]; then
        jar_file="$jar_file.jar"
    fi
    jar_file="jars/$jar_file"
    if [ ! -f "$jar_file" ]; then
        echo "File $jar_file does not exist"
    else
        echo "Removing $jar_file"
        rm "$jar_file"
    fi
}

# Start the script
# ./script --clean: Remove all the generated files
# ./script --clean xx.jar: Remove the specified jar file
# ./script: Generate the jar files for the project
# ./script --service microservice1 microservice2: Generate the jar files for the specified microservices
function start {
    if [ "$1" == "--clean" ]; then
        if [ "$2" ]; then
            echo "Removing specified jar file: $2"
            remove_jar_file "$2"
        else
            clean_up
        fi
    elif [ "$1" == "--service" ]; then
        if(( $# < 2 )); then
            echo "Please provide the microservices to generate the jar files"
            exit 1
        fi
        shift
        main "$@"
    else
        main "$@"
    fi
}


start "$@"