/**
 * 
 */
/**
 * The module for this assignment.   Please change the module name below to match the project name.
 * @author swong
 *
 */
module hw08 {
	requires java.desktop;
	requires transitive java.rmi;
	requires java.base;
	
	exports provided.discovery;
	exports provided.rmiUtils;
	exports provided.logger;
	exports provided.datapacket;
	exports provided.pubsubsync;
	exports provided.pubsubsync.impl;

	
	exports common.connector;
	exports common.receiver;
	exports common.adapter;
	exports common.connector.messages;
	exports common.receiver.messages;
	
	/**
	 *  Add exports for at least the following package and necessary sub-packages: 
	 *   - common
	 *   - student-defined message types and implementations
	 *   - any serialized support packages used by message implementations
	 */

	
}