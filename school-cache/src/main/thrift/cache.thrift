namespace java com.hopper.scala.school.cache

struct Response {
    1: optional string value;
}

service Cache {
    Response get(1: string key)
    void put(1: string key, 2: string value)
    void delete(1: string key)
}