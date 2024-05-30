1. A user can get all locks related to him/herself.
2. A user can add a lock
3. A owner can delete a lock
4. There should be an api to show the details of a lock
5. Allow user add a lock if the user is admin/owner

---

### API 1: Get all locks related to a user

**URL** : `/api/v1/locks/{user_id}/{lock_id}`
**Method** : `GET`
**Auth required** : YES
**Permissions required** : None
**Response example**

```json
{
  "id": 1,
  "userId": 123,
  "accessLevel": "admin",
  "expiration": "2020-12-12",
  "revoked": false,
  "lock":{
    "lockId": 1,
    "lockName": "lock1",
    "uid": "123",
    //....
    
  }
}
```


## Microservices

- Spring Boot
  • DiscoveryApplication :8761/
  • GatewayApplication :8222/
  • LockApplication :8300/
  • MessageApplication
  • SchoolApplication :8070/
  • UserApplication :8100/
  • ConfigServerApplication :8888/
  • StudentApplication :8090/

- API:

1. UserApplication http://localhost:8100/swagger-ui/index.html