import groovy.json.JsonSlurper

// convert json to csv
def jsonToCsv(String jsonFilPath, String csvFIlePath) {
    def jsonFile = new File(jsonFilPath)
    if (!jsonFile.exists()) {
        println "JSON file not found at $jsonFilPath"
        return
    }

    def jsonData = new JsonSlurper().parse(jsonFile)

    // get all keys
    def fields = jsonData[0].keySet().toList()

    def csvFile = new File(csvFIlePath)
    
    csvFile.withWriter { writer ->
        // write column name
        writer.writeLine(fields.join(","))

        // write data
        jsonData.each { entry ->
            writer.writeLine(fields.collect { field -> entry[field] ?: "" }.join(","))
        }
    }

    println "JSON has been successfully converted to CSV, and the result is saved to $csvFIlePath"
}

def jsonFilPath = "data.json"
def csvFIlePath = "data.csv"

jsonToCsv(jsonFilPath, csvFIlePath)
