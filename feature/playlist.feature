@tag
Feature: PlayList

  Scenario: Get song information
    Given rest endpoint URL and API resource for "playlist"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           200 | OK               |
    And I validate JSON reponse
    Then I compare resonse with valid schema "Multipleplaylist"

  Scenario: Get single song information
    Given rest endpoint URL, API resource and valid ID for "playlist"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           200 | OK               |
    And I validate JSON reponse
    Then I compare resonse with valid schema "Singleplaylist"

  Scenario: POST Playlist information
    Given I POST a request for JSON "playlist"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           201 | Created          |
    Then I validate JSON reponse
    And I compare resonse with valid schema "PostPlaylist"

  Scenario: Update playlist information
    Given I update a request for invaliddata "playlist" and "addupdate"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           204 | No Content       |

  Scenario: Update playlist information
    Given I update a request for invaliddata "playlist" and "removeupdate"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           501 | Not Implemented  |

  Scenario: Delete playlist information
    Given I delete a request for "playlist"
    Then I validate reponse code and message
      | Response Code | Response Message |
      |           204 | No Content       |
