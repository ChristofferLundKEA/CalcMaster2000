# Contributing to CalcMaster2000

Thank you for your interest in contributing to CalcMaster2000!  
Whether you want to improve the code, suggest features, or enhance the documentation, your efforts are greatly appreciated.

## Table of Contents

1. [How to Contribute](#how-to-contribute)
2. [Reporting Issues](#reporting-issues)
3. [Submitting a Pull Request](#submitting-a-pull-request)
4. [Development Setup](#development-setup)
5. [Coding Guidelines](#coding-guidelines)
6. [Testing Guidelines](#testing-guidelines)
7. [Code of Conduct](#code-of-conduct)

---

## How to Contribute

There are several ways you can contribute:

- **Report Bugs**: Found a bug? Open an issue describing the problem.  
- **Propose Features**: Have an idea for a new feature? Let us know by opening an issue.  
- **Submit Code Changes**: Fix bugs, add features, or refactor code and submit a pull request.  
- **Improve Documentation**: Documentation updates are always welcome.  

---

## Reporting Issues

When reporting an issue, please include the following:

1. A clear and concise description of the problem.  
2. Steps to reproduce the issue (if applicable).  
3. Logs or error messages (if any).  
4. Details about your environment (e.g., Java version, OS, etc.).  

---

## Submitting a Pull Request

Follow these steps to submit your code:

- **Fork the repository**: Click the "Fork" button at the top of this repository to create a personal copy of the repository.
- **Create a branch**: Before making any changes, create a new branch:
   
   git checkout -b feature/your-feature-name
## Making Changes to the Program

1. **Implement your changes:**  
   Add the new feature, fix the bug, or refactor code as needed. Ensure your work follows the coding guidelines outlined below.

2. **Write appropriate tests:**  
   If you’re adding a new feature or fixing a bug, include relevant tests to verify your changes (refer to the Testing Guidelines for more details). Tests should ensure that your code works as expected and doesn’t introduce new issues.

3. **Stage and commit your changes:**  
   Use meaningful and descriptive commit messages to make it easier for others to understand your changes:
   ```bash
   git commit -m "Implement [feature/fix]: Add [description of change]"
   
**Push your changes to your branch:**

Push your updates to the branch in your forked repository:
   git push origin feature/your-feature-name

**Submit a pull request:**
Go to the original repository on GitHub and click the "New Pull Request" button. Include the following in your pull request description:

- A clear summary of your changes.
- Any relevant details about why the change is necessary.
- Any additional context or references (e.g., links to issues or related discussions).

## Development Setup

**Prerequisites**
Make sure you have the following installed:
- **Java 21** (Azul JDK)
- **Maven**
- **MySQL** (or H2 for testing)

Refer to the README.md for specific version details.

## Steps to Set Up Locally

- **Clone the repository:**

git clone https://github.com/ChristofferLundKEA/CalcMaster2000
cd main

**Build the project using Maven:**

mvn clean install

**Run the application:**

mvn spring-boot:run

**Access the application** 

At http://localhost:8080/login

## Coding Guidelines

- **Follow code style:** Maintain consistency with existing formatting.  
- **Use meaningful names:** Ensure variables, methods, and classes clearly reflect their purpose.  
- **Add comments:** Document complex logic for better readability.  
- **Avoid hardcoding:** Use configuration files or environment variables instead.  

## Test Guidelines

- **Write tests:** Add unit tests for independent components and integration tests for interactions.  
- **Run tests:** Ensure all tests pass locally before submitting a pull request.  

To run tests locally:  
```bash
mvn test
