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

    def "Setting works in the event of collisions"() {
        given:
            FixedHashMap hashMap = new FixedHashMap(2)
        when:
            hashMap.set(30.toString(), "10")
            hashMap.set(100.toString(), "20")
        then:
            hashMap.get(30.toString()) == "10"
            hashMap.get(100.toString()) == "20"
    }
}
