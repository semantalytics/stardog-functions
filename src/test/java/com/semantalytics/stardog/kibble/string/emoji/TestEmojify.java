package com.semantalytics.stardog.kibble.string.emoji;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class TestEmojify extends AbstractStardogTest {

  

    @Test
    public void testemojify() {



        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                    "select ?result where { bind(emoji:emojify(\"A :cat:, :dog: and a :mouse: became friends<3. For :dog:'s birthday party, they all had :hamburger:s, :fries:s, :cookie:s and :cake:.\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = Literal.str((Literal)aResult.next().value("result").get());

                assertEquals("A \uD83D\uDC31, \uD83D\uDC36 and a \uD83D\uDC2D became friends❤️. For \uD83D\uDC36's birthday party, they all had \uD83C\uDF54s, \uD83C\uDF5Fs, \uD83C\uDF6As and \uD83C\uDF70.", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testIsEmojiTooFewArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:emojify() as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
    
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
    }

    @Test
    public void testIsEmojiTooManyArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:emojify(\"star\", \"dog\") as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
   
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet.size()).isZero();
        assertThat(aResult.hasNext()).isFalse();

    }

    @Test
    public void testIsEmojiWrongTypeOfArgIri() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:emojify(<http://example.com>) as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
    
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet.size()).isZero();
        assertThat(aResult.hasNext()).isFalse();

    }

    @Test
    public void testIsEmojiWrongTypeOfArgInt() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:emojify(1) as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
     
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();

    }

}
