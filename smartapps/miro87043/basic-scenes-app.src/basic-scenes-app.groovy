/**
 *  testing dynamic page
 *
 *  Copyright 2016 Micah Roth
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Basic Scenes App",
    namespace: "miro87043",
    author: "Micah Roth",
    description: "I'm learning dynamic pages\r\n",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


 preferences {
 	page(name: "trigger", title: "Select the control switch", nextPage: "onDevices", install: false) {
    	section {
        	input "controlSwitch","capability.switch", title: " ", required: true
            }
        }    
    
    page(name: "onDevices", nextPage: "offDevices")
    page(name: "offDevices")
}

def onDevices() {
    dynamicPage(name: "onDevices", title: "Turn these devices on", install: false, uninstall: true) {

        section {
        		input "switches", "capability.switch", title: "Select Switches", multiple: true, required: false
        }
        section {
            input name: "dimmer1", type: "capability.switchLevel", title: "Dimmer",
                  description: null, multiple: false, required: false, submitOnChange: true 
        }

        if (dimmer1) {
            section {
                input name: "dimmerLevel1", type: "number", title: "Level to dim lights to...", required: true 
            }
        	section {
            	input name: "dimmer2", type: "capability.switchLevel", title: "Dimmer",
                  description: null, multiple: false, required: false, submitOnChange: true 
       	    }
        }    

        if (dimmer2) {
            section {
                input name: "dimmerLevel2", type: "number", title: "Level to dim lights to...", required: true 
            }
        	section {
            	input name: "dimmer3", type: "capability.switchLevel", title: "Dimmer",
                  	description: null, multiple: false, required: false, submitOnChange: true 
        	}
         }   

        	if (dimmer3) {
            	section {
                	input name: "dimmerLevel3", type: "number", title: "Level to dim lights to...", required: true 
            	}
       	 	}
        	
            
    }
}

def offDevices() {
	dynamicPage(name: "offDevices", title: "Turn these devices off", install: true, uninstall: true) {
    	section {
        	input("offSwitches", "capability.switch", title: "Switches", multiple: true, required: false)
            input("offDimmers", "capability.switchLevel", title: "Dimmers", multiple: true, required: false)
        }
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(controlSwitch, "switch", theHandler)
    
}

def theHandler(evt) {
	if(evt.value == "on") {
    	
        def dimmerLevel1 = dimmerLevel1
        def dimmerLevel2 = dimmerLevel2
        def dimmerLevel3 = dimmerLevel3
        
        switches.on()
        offSwitches.off()
        offDimmers.setLevel(0)
    	dimmer1.setLevel(dimmerLevel1)
        dimmer2.setLevel(dimmerLevel2)
        dimmer3.setLevel(dimmerLevel3)        
        
    }
    if(evt.value == "off") {
    	
    	switches.off()
        dimmer1.setLevel(0)
        dimmer2.setLevel(0)
        dimmer3.setLevel(0)
        
    }
}
