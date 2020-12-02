Feature: Employee

  @test
  Scenario Outline: Add Employee
    Given an Employer
    And I am on the Benefits Dashboard page
    When I select Add Employee
    Then I should be able to enter employee details "<Name>","<Dependents>"
    And the employee should save
    And I should see the employee in the table
    And the benefit cost calculations are correct

    Examples: 
      | Name        | Dependents |
      | User Test27 |          2 |

  @test
  Scenario Outline: Edit Employee
    Given an Employer
    And I am on the Benefits Dashboard page
    When I select the Action Edit
    Then I can edit employee details "<Dependents>"
    And the data should change in the table

    Examples: 
      | Dependents |
      |          1 |

  @test
  Scenario: Delete Employee
    Given an Employer
    And I am on the Benefits Dashboard page
    When I click the Action X
    Then the employee should be deleted
