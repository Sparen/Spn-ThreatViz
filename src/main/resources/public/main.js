//Tutorial used: https://reactarmory.com/guides/learn-react-by-itself/react-basics

//Shorthand for creating an element
//First argument is the type (a/div/etc), second argument is element's attributes/props, remaining are children elements
const rce = React.createElement;

/* ---------- Standard Site Elements ---------- */

//Website banner
const banner = rce('div', 
    {className: 'stvheader'},
    rce('h1', {}, "Sparen ThreatViz")
)

//Website Information
const subbanner = rce('div', 
    {className: 'stvctrtxt'},
    rce('p', {}, 
        "Welcome to Sparen's Threat Visualizer. Here you can access and search through a snapshot of the ",
        rce('a', {target: '_blank', href: 'https://nvd.nist.gov/vuln/data-feeds'}, "National Vulnerability Database's data feeds"),
        "."
    )
)

//Footer
const footer = rce('footer', 
    {className: 'stvctrtxt'},
    rce('a', {href: 'https://github.com/Sparen/Spn-ThreatViz'}, "Source Code (GitHub)")
)

/* ---------- Primary Components ---------- */

//Search + Table Component. Takes in the pure JSON output and creates tables
class SearchComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: []
        };
        this.sortField = "lastModifiedDate"; //Default in practice is whatever order the database returns
        //Bind all non-React methods that access local variables
        this.runSearch = this.runSearch.bind(this);
        this.runSort = this.runSort.bind(this);
    }

    componentDidMount() {
    }

    render() {
        const { error, isLoaded, output } = this.state;
        if (error) {
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    rce('p', {}, "There was an error with the search.")
                )
            )
        } else if (!isLoaded) {
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    rce('p', {}, "Please type in your search terms in the input field and press Submit.")
                )
            )
        } else {
            //First, generate and format all the rows and contents, and create the table element to insert.
            //Header: Vendor, Product name, Attack Vector, Attack Complexity, Base Score, Base Severity, Publish Date, Last Mod Date
            var header = rce('thead', {},
                rce('tr', {className: 'tablerow'},
                    rce('th', {className: 'tableelement'}, "Vendor"),
                    rce('th', {className: 'tableelement'}, "Product Name"),
                    rce('th', {className: 'tableelement'}, "Attack Vector"),
                    rce('th', {className: 'tableelement'}, "Attack Complexity"),
                    rce('th', {className: 'tableelement'}, "Base Score"),
                    rce('th', {className: 'tableelement'}, "Base Severity"),
                    rce('th', {className: 'tableelement'}, "Publish Date"),
                    rce('th', {className: 'tableelement'}, "Last Modification Date")
                )
            );
            var rows = [];
            for (var i = 0; i < output.length; i += 1) {
                var currthreat = output[i];
                var vendor = currthreat.vendor;
                var productName = currthreat.productName;
                var versionValues = currthreat.versionValues;
                var description = currthreat.description;
                var attackVector = currthreat.attackVector;
                var attackComplexity = currthreat.attackComplexity;
                var privilegesRequired = currthreat.privilegesRequired;
                var userInteraction = currthreat.userInteraction;
                var confidentialityImpact = currthreat.confidentialityImpact;
                var integrityImpact = currthreat.integrityImpact;
                var availabilityImpact = currthreat.availabilityImpact;
                var baseScore = currthreat.baseScore;
                var baseSeverity = currthreat.baseSeverity;
                var exploitabilityScore = currthreat.exploitabilityScore;
                var impactScore = currthreat.impactScore;
                var publishDate = currthreat.publishDate;
                var lastModifiedDate = currthreat.lastModifiedDate;
                //First row for this entry
                var rowobj1 = rce('tr', {className: 'tablerow'},
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(vendor)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(productName)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(attackVector)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(attackComplexity)),
                    rce('td', {className: 'tableelement'}, returnNAIfInvalidScore(baseScore)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(baseSeverity)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(publishDate)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(lastModifiedDate))
                );
                rows.push(rowobj1);
                //Format version string
                var vstring = "";
                for (var j = 0; j < versionValues.length; j += 1) {
                    vstring += versionValues[j];
                    if (j != versionValues.length - 1) {
                        vstring += "; ";
                    }
                }
                var rowobj2 = rce('tr', {className: 'tablerow'},
                    rce('td', {className: 'tableelement', colSpan: '8'}, 
                        rce('p', {}, "Versions affected: " + vstring),
                        rce('p', {}, "Privileges Required: " + returnNAIfEmptyString(privilegesRequired) + "; User Interaction Required: " + returnNAIfEmptyString(userInteraction)),
                        rce('p', {}, "Confidentiality Impact: " + returnNAIfEmptyString(confidentialityImpact) + "; Integrity Impact: " + returnNAIfEmptyString(integrityImpact) + "; Availability Impact: " + returnNAIfEmptyString(availabilityImpact)),
                        rce('p', {}, "Exploitability Score: " + returnNAIfInvalidScore(exploitabilityScore) + "; Impact Score: " + returnNAIfInvalidScore(impactScore)),
                        rce('p', {}, "Description: " + returnNAIfEmptyString(description))
                    )
                );
                rows.push(rowobj2);
            }
            var tabletoinsert = rce('table', {className: 'stvtable'},
                header,
                rce('tbody', {}, rows)
            );
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    rce('br', {}),
                    //Generated table goes here
                    tabletoinsert
                )
            )
        }
    }

    runSearch() {
        //First, get the contents of the search bar
        var searchbarcontents = document.getElementById("searchfield").value;
        //alert("Search Field is: " + searchbarcontents);
        fetch("/api/stv/search/" + searchbarcontents)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        output: result,
                        error: null //Reset in case errored before; this allows future queries to go through.
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    runSort() {
        //Runs sort of the current sortField
        this.setState({
            output: sortByField(this.sortField, output)
        });
    }
}

//Component showcasing data in alternate format
class GraphComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: ""
        };
        //Bind all non-React methods that access local variables
    }

    componentDidMount() {
    }

    render() {
        const { error, isLoaded, output } = this.state;
        if (error) {
            return rce('div', {className: 'stvctrtxt'}, "Error")
        } else if (!isLoaded) {
            return rce('div', {className: 'stvctrtxt'}, "This Feature has not been implemented yet.")
        } else {
            return rce('div', {className: 'stvctrtxt'}, "This Feature has not been implemented yet.") //replace with output
        }
    }
}

//Debug Component for /test api call
class TestComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: ""
        };
    }

    componentDidMount() {
    fetch("/api/stv/test")
        .then(res => res.json())
        .then(
            (result) => {
                this.setState({
                    isLoaded: true,
                    output: result
                });
            },
            (error) => {
                this.setState({
                    isLoaded: true,
                    error
                });
            }
        )
    }

    render() {
        const { error, isLoaded, output } = this.state;
        if (error) {
            return rce('div', {className: 'stvctrtxt'}, "Error")
        } else if (!isLoaded) {
            return rce('div', {className: 'stvctrtxt'}, "Loading...")
        } else {
            return rce('div', {className: 'stvctrtxt'}, output)
        }
    }
}

//Main container for the application (Old)
const container = rce('div', 
    {},
    banner,
    subbanner,
    rce(SearchComponent, {}),
    rce('br', {}),
    footer
)

//Main container for the application (New)
class AppContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            active: 'DataTable'
        };
        //Bind all non-React methods that access local variables
        this.switchPaneDT = this.switchPaneDT.bind(this);
        this.switchPaneGR = this.switchPaneGR.bind(this);
        this.switchPaneTV = this.switchPaneTV.bind(this);
    }

    componentDidMount() {
    }

    //Originally, had one function that set w/ parameters, but JS automatically executed the switch on render
    switchPaneDT() {
        console.log("Switching to DataTable");
        this.setState({
            active: 'DataTable'
        });
    }
    switchPaneGR() {
        console.log("Switching to Graph");
        this.setState({
            active: 'Graph'
        });
    }
    switchPaneTV() {
        console.log("Switching to TestView");
        this.setState({
            active: 'TestView'
        });
    }

    render() {
        var active = this.state.active;
        var panel;
        if (active === 'DataTable') {
            panel = rce(SearchComponent, {});
        } else if (active === 'Graph') {
            panel = rce(GraphComponent, {});
        } else {
            panel = rce(TestComponent, {});
        }
        return (
            rce('div', 
                {},
                banner,
                subbanner,
                rce('div', {className: 'stvctrtxt'},
                    rce('button', {onClick: this.switchPaneDT}, "Data Table View"),
                    rce('button', {onClick: this.switchPaneGR}, "Graph View")
                ),
                rce('br', {}),
                panel,
                rce('br', {}),
                footer
            )
        );
     }
};

//Render the container
ReactDOM.render(
    rce(AppContainer, {}),
    document.getElementById('app')
)

/* ---------- Helper Functions ---------- */

function returnNAIfInvalidScore(score){
    if (score == -1) {
        return "N/A";
    } else {
        return score;
    }
}

function returnNAIfEmptyString(str){
    if (str == "") {
        return "N/A";
    } else {
        return str;
    }
}

//Wrapper for sortByField. If array is already sorted, reverses order. Otherwise sorts using sortByField
function sortOutputByField(field, arr) {
    var sorted = true;
    for (var i = 0; i < output.length - 1; i += 1) {
        if (arr[i][field] > arr[i + 1][field]) {
            sorted = false;
        }
    }
    if (sorted) {
        return arr.reverse();
    } else {
        return sortByField(field, arr, 0, arr.length);
    }
}

//Sorts the output array by the given field in place using quick sort.
function sortByField(field, arr, left, right) {
    var len = arr.length;
    var pivot;
    var partitionIndex;

    if (left < right) {
        pivot = right; //set pivot
        partitionIndex = sortPartition(field, arr, pivot, left, right);
        sortByField(field, arr, left, partitionIndex - 1);
        sortByField(field, arr, partitionIndex + 1, right);
    }
    return arr;
}

function sortPartition(field, arr, pivot, left, right){
    var pivotValue = arr[pivot][field];
    var partitionIndex = left;

    for(var i = left; i < right; i++){
        if(arr[i][field] < pivotValue){
            swap(arr, i, partitionIndex);
            partitionIndex++;
        }
    }
    swap(arr, right, partitionIndex);
    return partitionIndex;
}

function swap(arr, i, j){
    var temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}