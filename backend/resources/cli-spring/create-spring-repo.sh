#!/bin/bash

# Set default values
JAVA_VERSION=21
SPRING_BOOT_VERSION=3.4.2
GROUP_ID="com.example" # You can change this or prompt the user for it
DEPENDENCIES="web,data-jpa,h2,devtools,lombok" #define dependencies

# Prompt the user for the project name
read -p "Enter the Spring Boot project name: " PROJECT_NAME

# Validate project name (optional, but recommended)
if [[ -z "$PROJECT_NAME" ]]; then
  echo "Error: Project name cannot be empty."
  exit 1
fi

#  Prompt user for GitHub username
read -p "Enter your Github username: " GITHUB_USERNAME

# Initialize the Spring Boot project
echo "Initializing Spring Boot project '$PROJECT_NAME'..."
spring init \
    --dependencies="$DEPENDENCIES" \
    --build=maven \
    --java-version="$JAVA_VERSION" \
    --packaging=jar \
    --version="$SPRING_BOOT_VERSION" \
    --name="$PROJECT_NAME" \
    --groupId="$GROUP_ID" \
    "$PROJECT_NAME"

# Change directory to the project
cd "$PROJECT_NAME" || exit

# Initialize Git repository
echo "Initializing Git repository..."
git init

# Configure Git user (you might want to prompt the user for these, or set them globally)
git config user.name "$GITHUB_USERNAME"
git config user.email "$GITHUB_USERNAME@gmail.com" #modify

# Create GitHub repository
echo "Creating GitHub repository '$PROJECT_NAME'..."
gh repo create "$PROJECT_NAME" --public

# Add remote origin
echo "Adding remote origin..."
git remote add origin "https://github.com/$GITHUB_USERNAME/$PROJECT_NAME.git"

# Add all files, commit, and push
echo "Adding files, committing, and pushing to GitHub..."
git add .
git commit -m "Initial commit"
git push -u origin master

echo "Project '$PROJECT_NAME' created and pushed to GitHub successfully!"
exit 0

