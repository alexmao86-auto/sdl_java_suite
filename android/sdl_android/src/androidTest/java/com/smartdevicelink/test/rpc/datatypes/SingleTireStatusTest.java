package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.TPMS;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SingleTireStatus}
 */
public class SingleTireStatusTest extends TestCase {
	
	private SingleTireStatus msg;

	@Override
	public void setUp() {
		msg = new SingleTireStatus();
		
		msg.setStatus(TestValues.GENERAL_COMPONENTVOLUMESTATUS);
		msg.setTPMS(TestValues.GENERAL_TPMS);
		msg.setPressure(TestValues.GENERAL_FLOAT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		ComponentVolumeStatus status = msg.getStatus();
		TPMS tpms = msg.getTPMS();
		Float pressure = msg.getPressure();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_COMPONENTVOLUMESTATUS, status);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TPMS, tpms);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, pressure);
		
		// Invalid/Null Tests
		SingleTireStatus msg = new SingleTireStatus();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getStatus());
		assertNull(TestValues.NULL, msg.getTPMS());
		assertNull(TestValues.NULL, msg.getPressure());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SingleTireStatus.KEY_STATUS, TestValues.GENERAL_COMPONENTVOLUMESTATUS);
			reference.put(SingleTireStatus.KEY_TPMS, TestValues.GENERAL_TPMS);
			reference.put(SingleTireStatus.KEY_PRESSURE, TestValues.GENERAL_FLOAT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}