package com.example.cz2006ver2.Transport

/**
 * Object class to store attributes of a bus service and their bus details
 *
 * @property busNum Bus Service number
 * @property timing1 Timing of first incoming bus
 * @property timng2 Timing of second incoming bus
 * @property timing3 Timing of third incoming bus
 * @property colourStatus1 Colour reflecting crowd status on first bus
 * @property colourStatus2 Colour reflecting crowd status on second bus
 * @property colourStatus3 Colour reflecting crowd status on third bus
 * @property wheelStatus1 Wheelchair friendly status on first bus
 * @property wheelStatus2 Wheelchair friendly status on second bus
 * @property wheelStatus3 Wheelchair friendly status on third bus
 *
 */
data class BusInfo(var busNum: String, var timing1: String, var timing2: String, var timing3: String, var colourStatus1: Int
                   , var colourStatus2: Int, var colourStatus3: Int, var wheelStatus1: Int, var wheelStatus2: Int, var wheelStatus3: Int)
