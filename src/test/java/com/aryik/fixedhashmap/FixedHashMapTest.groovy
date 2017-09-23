package com.aryik.fixedhashmap
import spock.lang.*

/**
 * Created by Aryik Bhattacharya on 9/18/2017.
 */
class FixedHashMapTest extends Specification {

    def "Simple initialization succeeds"() {
        when:
            FixedHashMap hashMap = new FixedHashMap(10)
        then:
            notThrown(Exception)
            hashMap != null
            hashMap.getSize() == 10
    }

    def "Initialization without a size defaults to 10"() {
        when:
            FixedHashMap hashMap = new FixedHashMap()
        then:
            notThrown(Exception)
            hashMap != null
            hashMap.getSize() == 10
    }

    def "A negative size is not allowed"() {
        when:
            FixedHashMap hashMap = new FixedHashMap(-5)
        then:
            thrown(NegativeArraySizeException)
    }

    def "Setting a simple object succeeds"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            def test = hashMap.set("a key", 10)
        then:
            test
            notThrown(Exception)
    }

    def "Setting a larger object succeeds"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
            Object object = new FixedHashMap()
        when:
            def test = hashMap.set("a different key", object)
        then:
            test
            notThrown(Exception)
    }

    def "Overwriting a key succeeds"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            def test1 = hashMap.set("A key", 50)
            def test2 = hashMap.set("A key", 10)
        then:
            test1 && test2
            notThrown(Exception)
    }

    def "Setting fails when the size is zero"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(0)
        when:
            def test = hashMap.set("key", "value")
        then:
            !test
    }

    def "Setting fails when the size is exceeded"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(2)
        when:
            def test1 = hashMap.set("a key", "a value")
            def test2 = hashMap.set("a different key", "and a different value")
            def test3 = hashMap.set("the last key", "and the last value")
        then:
            test1
            test2
            !test3
    }

    def "Getting an unset key returns null"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(5)
        when:
            def unset = hashMap.get("key")
        then:
            unset == null
    }

    def "Get and set an object"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            hashMap.set("key", "value")
        then:
            hashMap.get("key") == "value"
    }

    def "Get a rewritten object"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            hashMap.set("key", "value")
            hashMap.set("key", "new value")
        then:
            hashMap.get("key") == "new value"
    }

    def "Collisions are properly handled"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(2)
        when:
            // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
            // have to change.
            hashMap.set(30.toString(), "30")
            hashMap.set(100.toString(), "100")
        then:
            hashMap.get(30.toString()) == "30"
            hashMap.get(100.toString()) == "100"
    }

    def "Setting and getting with multiple collisions works"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(3)
        when:
            // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
            // have to change. The collisions are not guaranteed if the size of the hash map is changed.
            hashMap.set(30.toString(), "30")
            hashMap.set(3000.toString(), "3000")
            hashMap.set(5.toString(), "5")
        then:
            hashMap.get(30.toString()) == "30"
            hashMap.get(3000.toString()) == "3000"
            hashMap.get(5.toString()) == "5"
    }

    def "Overwrite a key after a collision"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(2)
        when:
        // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
        // have to change.
            hashMap.set(30.toString(), "30")
            hashMap.set(100.toString(), "100")
            hashMap.set(30.toString(), "thirty")
        then:
            hashMap.get(30.toString()) == "thirty"
            hashMap.get(100.toString()) == "100"
    }

    def "Traverse a linked list to retrieve a nonexistent key"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(3)
        when:
        // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
        // have to change. The collisions are not guaranteed if the size of the hash map is changed.
            hashMap.set(30.toString(), "30")
            hashMap.set(3000.toString(), "3000")
        then:
            hashMap.get(30.toString()) == "30"
            hashMap.get(3000.toString()) == "3000"
            hashMap.get(5.toString()) == null
    }

    def "Deleting a nonexistent key returns null"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            def deletion = hashMap.delete("key")
        then:
            deletion == null
            notThrown(Exception)
    }

    def "Deleting an object succeeds"() {
        given:
            FixedHashMap hashMap = new FixedHashMap()
        when:
            hashMap.set("key", "value")
        then:
            hashMap.delete("key") == "value"
            hashMap.get("key") == null
    }

    def "Deleting an object succeeds with a collision"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(2)
        when:
        // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
        // have to change.
            hashMap.set(30.toString(), "30")
            hashMap.set(100.toString(), "100")
        then:
            hashMap.delete(30.toString()) == "30"
            hashMap.delete(100.toString()) == "100"
            hashMap.get(30.toString()) == null
            hashMap.get(100.toString()) == null
    }

    def "Traverse a linked list to delete a nonexistent key"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(3)
        when:
        // Collisions in hash codes were found by trial and error. If the hashing function changes, this test will
        // have to change. The collisions are not guaranteed if the size of the hash map is changed.
            hashMap.set(30.toString(), "30")
            hashMap.set(3000.toString(), "3000")
        then:
            hashMap.delete(5.toString()) == null
            hashMap.delete(30.toString()) == "30"
            hashMap.delete(3000.toString()) == "3000"
            hashMap.get(30.toString()) == null
            hashMap.get(3000.toString()) == null
    }

    def "Determine the load when size is 0"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(0)
        when:
            def load = hashMap.load()
        then:
            load == 0.0f
            notThrown(ArithmeticException)
    }

    def "Determine the load when no items are in the map"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(5)
        when:
            def load = hashMap.load()
        then:
            load == 0.0f
    }

    def "Determine the load normally"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(10)
        when:
            hashMap.set("key", "value")
            hashMap.set("key2", "value2")
            hashMap.set("key3", "value3")
            def load = hashMap.load()
        then:
            load == 0.3f
    }
}
