package ca.wbac.conway;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LifeTest {

    Life fixture;

    @Before
    public void setUp() {
        fixture = new Life(0b011100000000000, 5, 5);
    }

    @Test
    public void shouldGetIndexFromCoordinate() {
        assertThat(fixture.getIndex(0, 1, 4), is(4));
        assertThat(fixture.getIndex(3, 1, 4), is(7));
    }
    
    @Test
    public void shouldDieIfUnderOrOverPopulated() {
        assertThat(fixture.dies(1), is(true));
        assertThat(fixture.dies(5), is(true));
    }

    @Test
    public void shouldGrowIfPopulationIsThree() {
        assertThat(fixture.grow(3), is(true));
        assertThat(fixture.grow(1), is(false));
    }
    
    @Test
    public void shouldCountBitsInAnInt() {
        assertThat(fixture.countBits(0b111), is(3));
    }

    @Test
    public void shouldMaskAnArea() {
    	assertThat(fixture.maskArea(0, 0, 3), is(0b11011));
        assertThat(fixture.maskArea(1, 1, 3), is(0b111111111));
        assertThat(fixture.maskArea(2, 1, 4), is(0b111011101110));
        assertThat(fixture.maskArea(2, 2, 4), is(0b1110111011100000));
        assertThat(fixture.maskArea(0, 2, 4), is(0b11001100110000));
        assertThat(fixture.maskArea(2, 0, 4), is(0b11101110));
        assertThat(fixture.maskArea(2, 0, 3), is(0b110110));
    }
    
    @Test
    public void shouldReturnTrueIfAlive() {
    	assertThat(fixture.isAlive(1, 0, 0, 3), is(1));
    	assertThat(fixture.isAlive(0, 1, 0, 3), is(0));
    }
    
    @Test
    public void shouldCountNeighbours() {
    	assertThat(fixture.countNeighbours(0b1011, 0, 0, 3), is(2));
    	assertThat(fixture.countNeighbours(0b1011, 2, 0, 3), is(1));
    	assertThat(fixture.countNeighbours(0, 2, 2, 3), is(0));
    }
    
    @Test
    public void shouldRotate() {
    	assertThat(fixture.next(0b010010010, 3, 3, 3), is(0b0000111000));
    	assertThat(fixture.next(0b0000111000, 3, 3, 3), is(0b010010010));
    }
    
    @Test
    public void shouldRun() {
    	fixture.run(10);
    }
    
}
