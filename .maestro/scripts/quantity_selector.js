const baseUrl = env_base_url;

const URL_TO_WIRE_MOCK_MAPPING = {
  increase: "a2afd1d3-de4f-38b0-9f9a-feef1cf3071a",
  decrease: "d944d90c-5a30-3195-9c7e-cc59239c09bc",
  getData: "261fc3ff-bf5f-3e36-98e4-84afefc2eec7",
};

function increaseItems(requestedResponse) {
  const url = `${baseUrl}/__admin/mappings/${URL_TO_WIRE_MOCK_MAPPING.increase}`;

  if (requestedResponse === "error") {
    const errorMessage = "items cannot be more than 2";
    const errorRes = http.put(url, {
      body: `{ 
        "request" : {
          "url" : "/increase",
          "method" : "POST"
        },
        "response": { "status": 500, "jsonBody": { "message": "${errorMessage}" } }
      }`,
    });
    // console.log(`error:  ${JSON.stringify(errorRes)}`);
  } else {
    const res = http.put(url, {
      body: `{ 
        "request" : {
            "url" : "/increase",
            "method" : "POST"
        },
        "response": { "status": 200, "jsonBody": { "numItems": ${requestedResponse} } }
      }`,
    });
    // console.log(`res:  ${JSON.stringify(res)}`);
  }
}

function decreaseItems(requestedResponse) {
  const url = `${baseUrl}/__admin/mappings/${URL_TO_WIRE_MOCK_MAPPING.decrease}`;

  if (requestedResponse === "error") {
    const errorMessage = "number of items cannot be less than 0";
    const errorRes = http.put(url, {
      body: `{ 
        "request" : {
          "url" : "/decrease",
          "method" : "POST"
        },
        "response": { "status": 500, "jsonBody": { "message": "${errorMessage}" } }
      }`,
    });
    // console.log(`errorRes: ${JSON.stringify(errorRes)}`);
  } else {
    const res = http.put(url, {
      body: `{ 
        "request" : {
          "url" : "/decrease",
          "method" : "POST"
        },
        "response": { "status": 200, "jsonBody": { "numItems": ${requestedResponse} } }
      }`,
    });
    // console.log(`res: ${JSON.stringify(res)}`);
  }
}

function updateMocks() {
  const actionType = env_action_type;
  const response = env_response;

  switch (actionType) {
    case "increase":
      increaseItems(response);
      break;

    case "decrease":
      decreaseItems(response);
      break;
  }
}

updateMocks();
