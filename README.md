# JUnit5 + H2 + JPA
## 1. Repository tests
### Task 1 — Save and retrieve
Create a repository test that:
1. Saves a new object in the database
2. Retrieves the object using findById
3. Verify that the fields are correct
   Requirements:
* Use @DataJpaTest
* Use an in-memory H2 database
* Use TestEntityManager or repository directly
### Task 2 — Custom Query
Make a repository test where the model has at least one of the following:
* findByName(...)
* findByStatus(...)
* findByCreatedBefore(...)
  The test should:
* Create multiple entities
* Run the query
* Verify that only the correct object is returned
### Task 3 — Constraint / Exception
Create a test that ensures that the repository throws an exception for incorrect data, e.g.:
* unique field
* not null
* validation
  The test should use:
  assertThrows(DataIntegrityViolationException.class, ...)
## 2. Service tests (with Mockito)
### Task 4 — Mocked repository in service
Mock the repository and write a service test that:
1. When service.create(obj) is called:
* verify that repository.save(obj) is executed
2. When service.get(id) is called:
* mock repository.findById(id)
* verify that the correct object is returned
  Requirements:
* @ExtendWith(MockitoExtension.class)
* @Mock repository
* @InjectMocks service
### Task 5 — Exception in service
The service method should throw its own exception, e.g. NotFoundException.
The test should:
* mock repository.findById(id) to return empty Optional.empty()
* use assertThrows
* verify that the message is correct
### Task 6 — Check interactions
Create a test that ensures:
* repository.delete(id) is called exactly once
* repository.save(..) is never called
  Use verify(mock, times(1)) and verify(mock, never()).
## 3. MockMvc Tests (Controller)
### Task 7 — GET Endpoint
Write a MockMvc test for a GET endpoint:
1. Mock your service layer
2. Call /api/whatever/{id}
3. Verify:
* HTTP 200
* JSON fields contain correct values
  Requirements:
* @WebMvcTest
* @MockBean service
### Task 8 — POST Endpoint
Write a test for POST that:
1. Sends JSON with MockMvc
2. Mocks service.create()
3. Verify:
* HTTP 201
* Response body contains id or correct object
* Content-Type = application/json
### Task 9 — Validation / 400 error
Write a test where:
* A POST sends invalid JSON (missing required field)
* Controller has @Valid on request body
* The test verifies that it will be HTTP 400
  Can be anything: wrong length, null, max/min etc.
### Task 10 — Exception mapping
Controller should throw a service exception, e.g. NotFoundException.
The test should:
* Mock service to throw exception
* Verify that controller returns:
    * HTTP 404
    * JSON containing an error message
      (This works regardless of domain.)
# JPA Test Tasks
### Task 1 — Basic Persistence
Write a JPA test that:
1. Creates a simple entity (regardless of model)
2. Saves it with repository
3. Retrieves it with findById
4. Verify that:
* all fields saved correctly
* id generated
  Requirements:
* @DataJpaTest
* H2
* @AutoConfigureTestDatabase(replace = ANY)
### Task 2 — Update
Create a test that:
1. Saves an entity
2. Changes a field (e.g. name/status)
3. Saves again
4. Retrieves the entity and verifies that the field has changed
### Task 3 — Delete
Run a test that:
1. Inserts a entity
2. Deletes it
3. Verify:
* repository.findById returns empty
* repository.count() decreased
## Custom Queries
### Task 4 — findByX
Create a test where repository has a custom method, e.g.:
* findByName(String name)
* findByStatus(String status)
* findByAmountGreaterThan(BigDecimal amount)
  The test should:
* save multiple entities
* call the query
* verify filtering works
### Task 5 — Query with multiple criteria
Make a method like:
List<Entity> findByNameAndActive(String name, boolean active);
The test should create entities that cover:
* matches both fields
* matches one field
* matches nothing
## Relationships
### Task 6 — ManyToOne
The model should have a relationship, e.g. "Order → Customer" or any parent-child.
In the test:
1. Create parent
2. Save
3. Create child with parent
4. Save
5. Get child and verify parent is correct
### Task 7 — OneToMany
Do a test where:
1. Create parent
2. Add multiple children to a list
3. Save parent
4. Verify that children were saved and the links are correct