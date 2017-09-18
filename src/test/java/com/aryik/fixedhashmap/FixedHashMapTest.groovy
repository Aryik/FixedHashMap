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
}
