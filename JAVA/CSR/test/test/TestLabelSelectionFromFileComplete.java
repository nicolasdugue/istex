package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.diachronism.LabelDiachronism;
import model.featureselection.LabelSelectionFromFile;

public class TestLabelSelectionFromFileComplete {

	LabelSelectionFromFile ls;
	LabelSelectionFromFile ls2;
	LabelDiachronism diachro;
	@Before
	public void setUp() throws Exception {
		ls = new LabelSelectionFromFile("exemples/P1.dcsl");
		ls2 = new LabelSelectionFromFile("exemples/P2.dcsl");
		diachro = new LabelDiachronism(ls, ls2);
	}


	@Test
	public void testGetNbCluster() {
		assertEquals(3, ls.getNbCluster());
		assertEquals(2, ls2.getNbCluster());
	}
	@Test
	public void testLabelSetCluster() {
		assertTrue(ls.getLabelSet(0).contains("patient") && ls.getLabelSet(0).contains("dementia") && ls.getLabelSet(0).contains("disorder") && ls.getLabelSet(0).contains("depression") && ls.getLabelSet(0).contains("percnt")&& ls.getLabelSet(0).contains("test") && ls.getLabelSet(0).contains("impairment") && ls.getLabelSet(0).contains("treatment") && ls.getLabelSet(0).contains("score")&& ls.getLabelSet(0).contains("symptom")&& ls.getLabelSet(0).contains("scale")&& ls.getLabelSet(0).contains("adult"));
	}
	@Test
	public void testSizeCluster() {
		assertEquals(12, ls.getLabelSet(0).size());
		assertEquals(12, ls.getLabelSet(1).size());
		assertEquals(19, ls.getLabelSet(2).size());
		assertEquals(13, ls2.getLabelSet(0).size());
		assertEquals(16, ls2.getLabelSet(1).size());
	}

	@Test
	public void testGetFeatureValue() {
		assertEquals(2, ls.getFeatureValue(ls.getIndexOfColLabel("score"), 0), 0.0001);
		assertEquals(1, ls.getFeatureValue(ls.getIndexOfColLabel("score"), 2), 0.0001);
		assertEquals(2, ls.getFeatureValue(ls.getIndexOfColLabel("adult"), 0), 0.0001);
		assertEquals(1, ls.getFeatureValue(ls.getIndexOfColLabel("adult"), 2), 0.0001);
	}
	
	@Test
	public void testp_s_t() {
		assertEquals(0, diachro.getP_t_knowing_s(0, 0), 0.0001);
		assertEquals(0, diachro.getP_s_knowing_t(0, 0), 0.0001);
		assertEquals(1.0/36, diachro.getP_t_knowing_s(0, 1),0.0001);//dementia
		assertEquals(4.0/36, diachro.getP_s_knowing_t(0, 1),0.0001);
		assertEquals(7.0/25, diachro.getP_t_knowing_s(1, 0),0.0001);//protein, gene, process are common
		assertEquals(6.0/29, diachro.getP_s_knowing_t(1, 0),0.0001);
		assertEquals(0, diachro.getP_t_knowing_s(1, 1), 0.0001);
		assertEquals(0, diachro.getP_s_knowing_t(1, 1), 0.0001);
		assertEquals(0, diachro.getP_t_knowing_s(2, 0), 0.0001);
		assertEquals(0, diachro.getP_s_knowing_t(2, 0), 0.0001);
		assertEquals(16.0/36, diachro.getP_t_knowing_s(2, 1), 0.0001);//care, program, home,family, nursing,resident
		assertEquals(17.0/40, diachro.getP_s_knowing_t(2, 1), 0.0001);
	}
	@Test
	public void testp_a_s() {
		assertEquals(1.0/36, diachro.getPa_s(0), 0.0001);
		assertEquals(7.0/25, diachro.getPa_s(1), 0.0001);
		assertEquals(16.0/36, diachro.getPa_s(2), 0.0001);
	}
	@Test
	public void testp_a_t() {
		assertEquals(6.0/29, diachro.getPa_t(0), 0.0001);
		assertEquals(((4.0/36 )+(17.0/40))/2.0, diachro.getPa_t(1), 0.0001);
	}
	
	@Test
	public void testA_s() {
		assertEquals(((1.0/36) + (7.0/25)+ (16.0/36))/3, diachro.getA_s(), 0.0001);
	}
	@Test
	public void testA_t() {
		assertEquals((6.0/29 + ((4.0/36 )+(17.0/40))/2.0)/2, diachro.getA_t(), 0.0001);
	}

}
