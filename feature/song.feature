@tag
Feature: Daily Song API

  Scenario: Get song information
    Given rest endpoint URL and API resource for "song"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           200 | OK               |
    And I validate JSON reponse
    Then I compare resonse with valid schema "Multiplesong"

  Scenario: Get single song information
    Given rest endpoint URL, API resource and valid ID for "song"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           200 | OK               |
    And I validate JSON reponse
    Then I compare resonse with valid schema "Singlesong"

  Scenario: POST song information
    Given I POST a request for "song"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           201 | Created          |
    Then I validate JSON reponse
    And I compare resonse with valid schema "Singlesong"

  Scenario: Update song information
    Given I update a request for "song" and "updatedata"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           501 | Not Implemented  |

  Scenario: Delete song information
    Given I delete a request for "song"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           204 | No Content       |
    And rest endpoint URL, API resource and valid ID for "song"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           200 | OK               |
    And I validate the content of the response
