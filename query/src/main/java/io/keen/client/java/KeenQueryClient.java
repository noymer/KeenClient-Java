package io.keen.client.java;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.keen.client.java.exceptions.KeenQueryClientException;
import io.keen.client.java.exceptions.ServerException;
import io.keen.client.java.http.HttpHandler;
import io.keen.client.java.http.OutputSource;
import io.keen.client.java.http.Request;
import io.keen.client.java.http.Response;
import io.keen.client.java.http.UrlConnectionHttpHandler;
import io.keen.client.java.result.DoubleResult;
import io.keen.client.java.result.FunnelResult;
import io.keen.client.java.result.Group;
import io.keen.client.java.result.GroupByResult;
import io.keen.client.java.result.IntervalResult;
import io.keen.client.java.result.IntervalResultValue;
import io.keen.client.java.result.ListResult;
import io.keen.client.java.result.LongResult;
import io.keen.client.java.result.MultiAnalysisResult;
import io.keen.client.java.result.QueryResult;
import io.keen.client.java.result.StringResult;

/**
 * <p>
 * KeenQueryClient provides all of the functionality required to execute the basic queries
 * supported by the <a href="https://keen.io/docs/api/#analyses">Data Analysis API</a>.
 * <p> This includes Count, Count Unique, Sum, Average, Maximum, Minimum, Median, Percentile,
 * Select Unique, Funnel and Multi-Analysis. It does not include Extractions, Saved Queries or
 * Query Caching.
 *
 * @author claireyoung, baumatron, masojus
 * @since 1.0.0
 */
public class KeenQueryClient {
    private static final String ENCODING = "UTF-8";
    private final KeenJsonHandler jsonHandler;
    private final RequestUrlBuilder requestUrlBuilder;
    private final KeenProject project;
    private final HttpHandler httpHandler;

    /**
     * Gets the default project that this {@link KeenQueryClient} is using.
     *
     * @return The {@link KeenProject}.
     */
    public KeenProject getProject() {
        return project;
    }

    /**
     * Count query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/#count
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return  the count query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public long count(String eventCollection, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.COUNT)
                .withEventCollection(eventCollection)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return queryResultToLong(result);
    }

    /**
     * Count Unique query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/#count-unique
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The count unique query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public long countUnique(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.COUNT_UNIQUE)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return queryResultToLong(result);
    }

    /**
     * Minimum query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#minimum-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The minimum query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double minimum(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.MINIMUM)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result =  execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Maximum query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#maximum-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The response from the server in the "result" map.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double maximum(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.MAXIMUM)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Average query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#average-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The average query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double average(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.AVERAGE)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Median query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#median-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The median query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double median(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.MEDIAN)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result =  execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Percentile query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#percentile-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param percentile     The percentile.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The percentile query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double percentile(String eventCollection, String targetProperty, Double percentile, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.PERCENTILE)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withPercentile(percentile)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Sum query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#sum-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The sum response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public double sum(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.SUM)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result =  execute(queryParams);
        return queryResultToDouble(result);
    }

    /**
     * Select Unique query with only the required arguments.
     * Query API info here: https://keen.io/docs/api/reference/#select-unique-resource
     *
     * @param eventCollection     The name of the event collection you are analyzing.
     * @param targetProperty     The name of the property you are analyzing.
     * @param timeframe     The {@link RelativeTimeframe} or {@link AbsoluteTimeframe}.
     * @return The select unique query response.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public QueryResult selectUnique(String eventCollection, String targetProperty, Timeframe timeframe) throws IOException {
        Query queryParams = new Query.Builder(QueryType.SELECT_UNIQUE)
                .withEventCollection(eventCollection)
                .withTargetProperty(targetProperty)
                .withTimeframe(timeframe)
                .build();
        QueryResult result = execute(queryParams);
        return result;
    }

    /**
     * This is the most flexible way to run a query. Use {@link Builder} to
     * build all the query arguments to run the query.
     *
     * @param request     The {@link KeenQueryRequest} to be executed.
     * @return The {@link QueryResult} result.
     * @throws IOException If there was an error communicating with the server or
     * an error message received from the server.
     */
    public QueryResult execute(KeenQueryRequest request) throws IOException {
        Map<String, Object> queryArgs = request.constructRequestArgs();
        URL url = request.getRequestURL(this.requestUrlBuilder, this.project.getProjectId());

        Map<String, Object> postResponse = postRequest(project, url, queryArgs);

        QueryResult result = null;
        boolean isFunnel = request instanceof Funnel;
        boolean isMultiAnalysis = request instanceof MultiAnalysis;

        // If the request was a funnel, construct the appropriate response including
        // other response data that might be included, such as the 'actors' key.
        if (isFunnel) {
            result = constructFunnelResult(postResponse);
        } else {
            final Collection<String> groupByParams = request.groupedResponseExpected() ?
                    request.getGroupByParams() : Collections.<String>emptyList();

            // Construct a response for more generic query responses that
            // don't include anything more of interest than the 'result'
            // key
            result = constructQueryResult(
                    postResponse.get(KeenQueryConstants.RESULT),
                    request.groupedResponseExpected(),
                    request.intervalResponseExpected(),
                    isMultiAnalysis,
                    groupByParams);
        }

        return result;
    }

    private static QueryResult constructQueryResult(Object input,
                                                    boolean isGroupBy,
                                                    boolean isInterval) {
        return constructQueryResult(input, isGroupBy, isInterval, false, Collections.<String>emptyList());
    }

    private static QueryResult constructQueryResult(Object input,
                                                    boolean isGroupBy,
                                                    boolean isInterval,
                                                    boolean isMultiAnalysis,
                                                    Collection<String> groupByParams) {
        if (isGroupBy && (null == groupByParams || groupByParams.isEmpty())) {
            throw new IllegalArgumentException(
                    "If we expect a GroupByResult, then 'groupByParams' are required.");
        }

        QueryResult result = null;

        // below code determines what type of object QueryResult holds.
        if (input instanceof Integer) {
            Integer intValue = (Integer) input;
            result = new LongResult(intValue.longValue());
        } else if (input instanceof Long) {
            result = new LongResult((Long) input);
        } else if (input instanceof Double) {
            result = new DoubleResult((Double) input);
        } else if (input instanceof String) {
            result = new StringResult((String) input);
        } else if (input instanceof Map) {
            if (!isMultiAnalysis) {
                throw new IllegalStateException("Received a JSON dictionary result when not " +
                        "expecting a MultiAnalysisResult.");
            }

            result = constructMultiAnalysisResult(input);
        } else if (input instanceof List) {
            result = constructListResult(
                    input,
                    isGroupBy,
                    isInterval,
                    isMultiAnalysis,
                    groupByParams
            );
        }

        return result;
    }

    private static QueryResult constructMultiAnalysisResult(Object input) {
        // Multi-Analysis results:
        // - Simple: just a single JSON object with each key matching the label of a
        // sub-analysis that was specified in the 'analyses' field of the request.
        // - Group By: a list of JSON objects, each of which has all the sub-analysis keys just
        // like the simple result, plus a key(s) with the name(s) of the 'group_by'(s) as
        // specified in the request. There is *not* a 'result' key here.
        // - Interval : a list of interval results ('timeframe' and 'value' keys) where the
        // 'value' is a JSON object just like a simple result.
        // - Interval + Group By: a list of interval results ('timeframe' and 'value' keys)
        // where the 'value' is a list of JSON objects just like a group by result.
        Map<?, ?> inputMap = (Map)input;
        Map<String, QueryResult> subAnalysesResults =
                new HashMap<String, QueryResult>(inputMap.size());

        for (Map.Entry<?, ?> entry : inputMap.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (!(key instanceof String)) {
                throw new IllegalStateException("Somehow a parsed JSON key was not a String.");
            }

            if (value instanceof Map) {
                throw new IllegalStateException("Somehow a parsed JSON value for a simple " +
                        "MultiAnalysis result was a nested JSON dictionary.");
            }

            subAnalysesResults.put((String)key, constructQueryResult(value, false, false));
        }

        return new MultiAnalysisResult(subAnalysesResults);
    }

    private static QueryResult constructListResult(Object input,
                                                   boolean isGroupBy,
                                                   boolean isInterval,
                                                   boolean isMultiAnalysis,
                                                   Collection<String> groupByParams) {

        QueryResult result = null;
        // recursively construct the children of this...
        List<Object> listInput = (ArrayList<Object>)input;

        // if this is an IntervalResult, construct the IntervalResult object.
        if (isInterval) {
            result = constructIntervalResult(
                    listInput,
                    isGroupBy,
                    isMultiAnalysis,
                    groupByParams
            );
        } else if (isGroupBy) {
            // if this is a GroupByResult, construct the GroupByResult object.
            // Note that if this is both an Interval and GroupBy, the GroupBy
            // code will be called later from within constructIntervalResult()
            result = constructGroupByResult(listInput, groupByParams, isMultiAnalysis);
        } else {
            // else if this is just a List of QueryResult objects - for example,
            // Select Unique query returns a list of unique objects.
            List<QueryResult> listOutput = new ArrayList<QueryResult>();
            for (Object child : listInput) {
                // We don't expect this list to contain nested GroupByResults.
                QueryResult resultItem = constructQueryResult(child, false, false);
                listOutput.add(resultItem);
            }
            result = new ListResult(listOutput);
        }

        return result;
    }

    private static IntervalResult constructIntervalResult(List<Object> intervals,
                                                          boolean isGroupBy,
                                                          boolean isMultiAnalysis,
                                                          Collection<String> groupByParams) {
        List<IntervalResultValue> intervalResult = new ArrayList<IntervalResultValue>();

        for (Object child : intervals) {
            if (child instanceof Map) {
                Map<String, Object> inputMap = (HashMap<String, Object>) child;

                // If this is an interval, it should have keys "timeframe" and "value"
                if (inputMap.containsKey(KeenQueryConstants.TIMEFRAME)
                        && (inputMap.containsKey(KeenQueryConstants.VALUE))) {
                    AbsoluteTimeframe absoluteTimeframe;
                    Object timeframe = inputMap.get(KeenQueryConstants.TIMEFRAME);

                    if (timeframe instanceof Map) {
                        Map<String, String> hashTimeframe = (HashMap<String, String>) timeframe;
                        String start = hashTimeframe.get(KeenQueryConstants.START);
                        String end = hashTimeframe.get(KeenQueryConstants.END);
                        absoluteTimeframe = new AbsoluteTimeframe(start, end);
                    } else {
                        throw new IllegalStateException("IntervalResult Timeframe should be" +
                                "instanceof Map. Instead, it is " +
                                timeframe.getClass().getCanonicalName() + ".");
                    }

                    Object value = inputMap.get(KeenQueryConstants.VALUE);
                    QueryResult queryResultValue =
                            constructQueryResult(value, isGroupBy, false, isMultiAnalysis, groupByParams);

                    intervalResult.add(
                            new IntervalResultValue(absoluteTimeframe, queryResultValue));
                } else {
                    throw new IllegalStateException("IntervalResult is missing \"" +
                            KeenQueryConstants.TIMEFRAME + "\" and \"" +
                            KeenQueryConstants.VALUE + "\" keys.");
                }
            } else {
                throw new IllegalStateException("IntervalResult should be instanceof Map. " +
                        "Instead, it is " + child.getClass().getCanonicalName() + ".");
            }
        }

        return new IntervalResult(intervalResult);
    }

    private static GroupByResult constructGroupByResult(List<Object> groups,
                                                        Collection<String> groupByParams,
                                                        boolean isMultiAnalysis) {
        Map<Group, QueryResult> groupByResult = new HashMap<Group, QueryResult>();

        for (Object child : groups) {
            if (child instanceof Map) {
                Map<String, Object> inputMap = (HashMap<String, Object>) child;

                // Multi-Analysis group by results don't have a 'result' key. What's more, one
                // can label a sub-analysis 'result' so instead of looking for the 'result' key
                // just parse the entries into disjoint sets, one withs keys in the set of
                // 'group_by' parameters that were in the request, which will be the Group
                // properties, and the rest as the result(s) set.
                Map<String, Object> properties = new HashMap<String, Object>();
                Map<String, Object> results = new HashMap<String, Object>();

                for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
                    if (groupByParams.contains(entry.getKey())) {
                        properties.put(entry.getKey(), entry.getValue());
                    } else {
                        results.put(entry.getKey(), entry.getValue());
                    }
                }

                // In either case there should not be intervals or groups nested inside
                // GroupByResults but GroupByResults *can* be nested inside IntervalResults.
                QueryResult result;

                if (0 == results.size()) {
                    throw new IllegalStateException("There were no results in the GroupBy result.");
                } else if (isMultiAnalysis) {
                    // For Multi-Analysis, pass the entire dictionary of sub-analyses results.
                    result = constructQueryResult(
                            results,
                            false,
                            false,
                            isMultiAnalysis,
                            groupByParams);
                } else {
                    if (!results.containsKey(KeenQueryConstants.RESULT)) {
                        throw new IllegalStateException("Single Analysis GroupBy result is " +
                                "missing \"" + KeenQueryConstants.RESULT + "\" key.");
                    }

                    // For Single Analysis, peel out the 'result' key's value and put that in the
                    // GroupByResult.
                    result = constructQueryResult(
                            results.get(KeenQueryConstants.RESULT), false, false);
                }

                Group groupBy = new Group(properties);
                groupByResult.put(groupBy, result);
            } else {
                throw new IllegalStateException("GroupBy result should be instanceof Map. " +
                        "Instead, it is " + child.getClass().getCanonicalName() + ".");
            }
        }

        return new GroupByResult(groupByResult);
    }

    /**
     * Constructs a FunnelResult from a response object map. This map should include
     * a 'result' key and may include an optional 'actors' key as well.
     *
     * @param responseMap The server response, deserialized to a Map<String, Object>
     * @return A FunnelResult instance.
     */
    private static FunnelResult constructFunnelResult(Map<String, Object> responseMap) {

        // Create a result for the 'result' field of the funnel response. FunnelResult won't contain
        // intervals or groups, as those parameters aren't supported for Funnel.
        QueryResult funnelResult = constructQueryResult(
            responseMap.get(KeenQueryConstants.RESULT),
            false,
            false);

        if (!(funnelResult instanceof ListResult)) {
            throw new KeenQueryClientException("'result' property of response contained data of an unexpected format.");
        }

        ListResult actorsResult = null;
        // Check for any additional result data that has been returned and include it with the result.
        if (responseMap.containsKey(KeenQueryConstants.ACTORS))
        {
            QueryResult genericActorsResult = constructQueryResult(
                responseMap.get(KeenQueryConstants.ACTORS),
                false,
                false);

            if (!(genericActorsResult instanceof ListResult)) {
                throw new KeenQueryClientException("'actors' property of response contained data of an unexpected format.");
            }

            actorsResult = (ListResult)genericActorsResult;
        }

        return new FunnelResult((ListResult)funnelResult, actorsResult);
    }

    /**
     * Posts a request to the server in the specified project, using the given URL and request data.
     * The request data will be serialized into JSON using the client's
     * {@link io.keen.client.java.KeenJsonHandler}.
     *
     * @param project     The project in which the event(s) will be published; this is used to
     *                    determine the read key to use for authentication.
     * @param url         The URL to which the POST should be sent.
     * @param requestData The request data, which will be serialized into JSON and sent in the
     *                    request body.
     * @return The response from the server in the "result" map.
     * @throws IOException If there was an error communicating with the server.
     */
    private Map<String, Object> postRequest(KeenProject project, URL url,
                               final Map<String, ?> requestData) throws IOException {

        // Build an output source which simply writes the serialized JSON to the output.
        OutputSource source = new OutputSource() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(out, ENCODING);

                // in queries, requestData may be null.
                if (requestData != null && requestData.size() != 0) {
                    jsonHandler.writeJson(writer, requestData);
                }
            }
        };

        // If logging is enabled, log the request being sent.
        if (KeenLogging.isLoggingEnabled()) {
            try {
                StringWriter writer = new StringWriter();
                jsonHandler.writeJson(writer, requestData);
                String request = writer.toString();
                KeenLogging.log(String.format(Locale.US, "Sent request '%s' to URL '%s'",
                        request, url.toString()));
            } catch (IOException e) {
                KeenLogging.log("Couldn't log event written to file: ");
                e.printStackTrace();
            }
        }

        // Send the request.
        String readkey = project.getReadKey();
        Request request = new Request(url, "POST", readkey, source, null);
        Response response = httpHandler.execute(request);

        if (!response.isSuccess()) {
            throw new ServerException(response.body);
        }

        // Parse the response into a map.
        StringReader reader = new StringReader(response.body);
        Map<String, Object> responseMap;
        responseMap = this.jsonHandler.readJson(reader);

        // Check for an error code if no result was provided.
        if (null == responseMap.get(KeenQueryConstants.RESULT)) {
            // double check if result is null because there's an error (shouldn't happen but let's check)
            if (responseMap.containsKey(KeenQueryConstants.ERROR_CODE)) {
                String errorCode = responseMap.get(KeenQueryConstants.ERROR_CODE).toString();
                String message = responseMap.get(KeenQueryConstants.MESSAGE).toString();

                String errorMessage = "Error response received from server";
                if (errorCode != null) {
                    errorMessage += " " + errorCode;
                }
                if (message != null) {
                    errorMessage += ": " + message;
                }

                throw new KeenQueryClientException(errorMessage);
            }
        }

        // Return the entire response map
        return responseMap;
    }

    private long queryResultToLong(QueryResult result) throws KeenQueryClientException {
        if (result == null) {
            throw new NullPointerException("Query Error: expected long response type but received null.");
        }

        if (result.isLong()) {
            return result.longValue();
        } else {
            throw new IllegalStateException("Query Error: expected long response type.");
        }
    }

    private double queryResultToDouble(QueryResult result) throws KeenQueryClientException {
        if (result == null) {
            throw new NullPointerException("Query Error: expected double response type but received null.");
        }

        if (result.isDouble()) {
            return result.doubleValue();
        } else if (result.isLong()) {
            return (double)result.longValue();
        } else {
            throw new IllegalStateException("Query Error: expected double response type.");
        }
    }

    /**
     * Constructs a Keen Query client using a builder.
     *
     * @param builder The {@link Builder} from which to retrieve this client's interfaces and settings.
     */
    protected KeenQueryClient(Builder builder) {
        // Initialize final properties using the builder.
        httpHandler = builder.httpHandler;
        jsonHandler = builder.jsonHandler;
        requestUrlBuilder = new RequestUrlBuilder(KeenConstants.API_VERSION, builder.baseUrl);
        project = builder.project;
    }

    /**
     * <p>
     * Builder class for instantiating Keen Query clients.
     *
     * <p> This builder defaults to using HttpURLConnection to handle HTTP requests.
     * <p> This builder defaults to using JacksonJsonHandler for JSON handler.
     * <p> This builder defaults to using KeenConstants.SERVER_ADDRESS for base URL.
     *
     * @author claireyoung
     * @since 1.0.0
     */
    public static class Builder {
        private HttpHandler httpHandler;
        private KeenJsonHandler jsonHandler;
        private String baseUrl;
        private KeenProject project;

        /**
         * Builder to create a KeenQueryClient with {@link KeenProject}.
         *
         * @param project The {@link KeenProject} to use.
         */
        public Builder(KeenProject project) {
            this.project = project;
        }

        /**
         * Gets the default {@link HttpHandler} to use if none is explicitly set for this builder.
         *
         * This implementation returns a handler that will use {@link java.net.HttpURLConnection}
         * to make HTTP requests.
         *
         * Subclasses should override this to provide an alternative default {@link HttpHandler}.
         *
         * @return The default {@link HttpHandler}.
         * @throws Exception If there is an error creating the {@link HttpHandler}.
         */
        protected HttpHandler getDefaultHttpHandler() throws Exception {
            return new UrlConnectionHttpHandler();
        }

        /**
         * Gets the {@link HttpHandler} that this builder is currently configured to use for making
         * HTTP requests. If null, a default will be used instead.
         *
         * @return The {@link HttpHandler} to use.
         */
        public HttpHandler getHttpHandler() {
            return httpHandler;
        }

        /**
         * Sets the {@link HttpHandler} to use for making HTTP requests.
         *
         * @param httpHandler The {@link HttpHandler} to use.
         */
        public void setHttpHandler(HttpHandler httpHandler) {
            this.httpHandler = httpHandler;
        }

        /**
         * Sets the {@link HttpHandler} to use for making HTTP requests.
         *
         * @param httpHandler The {@link HttpHandler} to use.
         * @return This instance (for method chaining).
         */
        public Builder withHttpHandler(HttpHandler httpHandler) {
            setHttpHandler(httpHandler);
            return this;
        }

        /**
         * Gets the default {@link KeenJsonHandler} to use if none is explicitly set for this builder.
         *
         * Subclasses must override this to provide a default {@link KeenJsonHandler}.
         *
         * @return The default {@link KeenJsonHandler}.
         * @throws Exception If there is an error creating the {@link KeenJsonHandler}.
         */
        protected KeenJsonHandler getDefaultJsonHandler() throws Exception {
            return new JacksonJsonHandler();
        }

        /**
         * Gets the {@link KeenJsonHandler} that this builder is currently configured to use for
         * handling JSON operations. If null, a default will be used instead.
         *
         * @return The {@link KeenJsonHandler} to use.
         */
        public KeenJsonHandler getJsonHandler() {
            return jsonHandler;
        }

        /**
         * Sets the {@link KeenJsonHandler} to use for handling JSON operations.
         *
         * @param jsonHandler The {@link KeenJsonHandler} to use.
         */
        public void setJsonHandler(KeenJsonHandler jsonHandler) {
            this.jsonHandler = jsonHandler;
        }

        /**
         * Sets the {@link KeenJsonHandler} to use for handling JSON operations.
         *
         * @param jsonHandler The {@link KeenJsonHandler} to use.
         * @return This instance (for method chaining).
         */
        public Builder withJsonHandler(KeenJsonHandler jsonHandler) {
            setJsonHandler(jsonHandler);
            return this;
        }

        /**
         * Gets the base URL to use for queries.
         *
         * @return The base URL to use.
         */
        public String getBaseURL() { return baseUrl; }

        /**
         * Sets the Base URL to use for queries.
         *
         * @param baseUrl The base URL to use.
         */
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

        /**
         * Sets the Base URL to use for queries.
         *
         * @param baseUrl The base URL to use.
         * @return This instance (for method chaining).
         */
        public Builder withBaseUrl(String baseUrl) {
            setBaseUrl(baseUrl);
            return this;
        }

        /**
         * Gets the {@link KeenProject} to use for queries.
         *
         * @return The {@link KeenProject}.
         */
        public KeenProject getKeenProject() { return project; }

        /**
         * Sets the {@link KeenProject} to use for queries.
         *
         * @param project The Keen Project containing Project ID and read/write keys.
         */
        public void setKeenProject(KeenProject project) { this.project = project; }

        /**
         * Builds a new Keen query client using the interfaces which have been specified explicitly on
         * this builder instance via the set* or with* methods, or the default interfaces if none
         * have been specified.
         *
         * @return A newly constructed Keen client.
         * @throws IllegalArgumentException when the project is null.
         */
        public KeenQueryClient build() {
            try {
                if (httpHandler == null) {
                    httpHandler = getDefaultHttpHandler();
                }
            } catch (Exception e) {
                KeenLogging.log("Exception building HTTP handler: " + e.getMessage());
            }

            try {
                if (jsonHandler == null) {
                    jsonHandler = getDefaultJsonHandler();
                }
            } catch (Exception e) {
                KeenLogging.log("Exception building JSON handler: " + e.getMessage());
            }

            if (project == null) {
                throw new IllegalStateException("Cannot build KeenQueryClient with null project.");
            }

            if (baseUrl == null) {
                baseUrl = KeenConstants.SERVER_ADDRESS;
            }

            return buildInstance();
        }

        /**
         * Builds an instance based on this builder.
         * @return The new KeenQueryClient instance.
         */
        protected KeenQueryClient buildInstance() {
            return new KeenQueryClient(this);
        }
    }
}