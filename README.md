# Newsletter Signup Automation

[![Selenium CI](https://github.com/Aline-eng/newsletter-signup-automation/actions/workflows/ci.yml/badge.svg)](https://github.com/Aline-eng/newsletter-signup-automation/actions/workflows/ci.yml)

Automated UI test suite for the [Newsletter Signup form](https://aline-eng.github.io/NewsLetter-Signup/), built with **Selenium WebDriver**, **JUnit 5**, and the **Page Object Model**, running on every push and pull request via **GitHub Actions**, with build status reported to Slack.

This project was built as a hands-on lab to practice test automation fundamentals after completing manual testing training, following on from the [Applitools Test Automation University Selenium WebDriver course](https://testautomationu.applitools.com/selenium-webdriver-tutorial-java/).

## What's tested

Against the live Newsletter Signup page:

- **Successful signup** — a valid email is submitted and the confirmation message ("Thanks for subscribing!") appears with the submitted email echoed back.
- **Empty email submitted** — the form shows the validation error "Whoops! It looks like this is empty" and no confirmation appears.
- **Invalid email format submitted** — the form shows "Valid email required" and no confirmation appears.

The two validation cases run as a single `@ParameterizedTest`, covering both inputs from one method.

## Tech stack

| Layer | Tool |
|---|---|
| Language | Java 17 |
| Build tool | Maven |
| Browser automation | Selenium WebDriver 4.48.0 |
| Test framework | JUnit 5 (Jupiter 6.1.3) |
| Driver management | Selenium Manager (bundled — no manual ChromeDriver setup) |
| Design pattern | Page Object Model with Page Factory |
| CI/CD | GitHub Actions |
| Notifications | Slack (Incoming Webhook) |

## Project structure

```
newsletter-signup-automation/
├── .github/
│   └── workflows/
│       └── ci.yml              # GitHub Actions pipeline
├── src/
│   ├── main/java/org/automation/pages/
│   │   └── SignupPage.java     # Page Object — locators and page interactions
│   └── test/java/tests/
│       └── SignupTest.java     # Test cases and assertions
├── pom.xml                     # Maven dependencies and build config
└── README.md
```

## Prerequisites

- JDK 17 or later
- Maven 3.9+
- Google Chrome installed

No manual ChromeDriver download is needed — Selenium Manager resolves the correct driver version automatically.

## Running the tests locally

```bash
git clone https://github.com/Aline-eng/newsletter-signup-automation.git
cd newsletter-signup-automation
mvn test
```

Chrome will launch visibly during a local run. Test results print to the console, and a full report is written to `target/surefire-reports/`.

## Page Object Model

Page interactions live in `src/main/java/pages/SignupPage.java`, kept separate from the test logic in `src/test/java/tests/SignupTest.java`. Locators are declared with `@FindBy` and wired up via `PageFactory.initElements()`, so tests only ever call readable methods like `subscribe(email)` rather than touching CSS selectors directly. If the site's markup changes, only the Page Object needs updating.

## CI/CD pipeline

On every push or pull request to `main`, `.github/workflows/ci.yml`:

1. Checks out the code and sets up JDK 17 (with Maven dependency caching).
2. Runs `mvn test` — Chrome runs **headless** in this step, since the CI runner has no display. The test code detects this automatically via the `CI` environment variable that GitHub Actions sets.
3. Uploads the Surefire test reports as a downloadable build artifact, even on failure.
4. Posts a pass/fail build notification to Slack via an Incoming Webhook.

### Setting up Slack notifications on a fork

The Slack step reads a webhook URL from a repository secret so it's never exposed in the workflow file or logs:

1. Create a Slack app with an Incoming Webhook ([api.slack.com/apps](https://api.slack.com/apps)) for the channel you want notified.
2. In the repo: **Settings → Secrets and variables → Actions → New repository secret**, named `SLACK_WEBHOOK_URL`.

## Possible next steps

- Explicit waits (`WebDriverWait` / `ExpectedConditions`) for pages with async/animated content.
- Additional locator strategies (XPath, `By.name`) for broader practice.
- Cross-browser runs (Firefox, Edge) alongside Chrome.
- A TestNG version of the suite for comparison against JUnit 5.