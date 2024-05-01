@petAdmission
Feature: Feature for pet Admission
  Scenario Outline: scenario to submit pet admission
    Given genarate token to submit pet admission
    When built pet admission payload with following details
    |name    |status    |
    |<name>|<status>|
    Then add category into the payload
    |name|
    |String|
    Then add photoUrls into the payload
    |photoUrls|
    |String   |
    And add the tags into the payload
    |name |
    |string|
    |string1|
    And generate the pet admission request payload
    And validate the json schema of pet admission request payload
    And execute the pet admission POST request
    Examples:
    |name|status|
    |sam |available|