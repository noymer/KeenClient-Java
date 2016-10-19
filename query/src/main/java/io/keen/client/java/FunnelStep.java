/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.keen.client.java;

import java.util.HashMap;
import java.util.List;

/**
 * An object which represents a funnel step.
 * @author baumatron
 */
public class FunnelStep implements RequestParameter {
    
    // Required parameters
    final public String eventCollection;
    final public String actorProperty;
    final public Timeframe timeframe;
    
    // Optional parameters
    final public RequestParameterList<Filter> filters;
    
    public FunnelStep(
        String eventCollection,
        String actorProperty)
    {
        // timeframe can be unspecified for a funnel step, but only if it
        // has been specified at for the root request
        this(eventCollection, actorProperty, null, null);
    }
        
    public FunnelStep(
        String eventCollection,
        String actorProperty,
        Timeframe timeframe)
    {
        this(eventCollection, actorProperty, timeframe, null);
    }
    
    public FunnelStep(
        String eventCollection,
        String actorProperty,
        Timeframe timeframe,
        List<Filter> filters)
    {
        if (null == eventCollection || eventCollection.isEmpty())
        {
            throw new IllegalArgumentException("FunnelStep parameter eventCollection must be provided.");
        }
        
        if (null == actorProperty || actorProperty.isEmpty())
        {
            throw new IllegalArgumentException("FunnelStep parameter actorProperty must be provided.");
        }
        
        this.eventCollection = eventCollection;
        this.actorProperty = actorProperty;
        
        // Timeframe may be null
        this.timeframe = timeframe;
        
        if (null != filters && !filters.isEmpty())
        {
            this.filters = new RequestParameterList(filters);
        }
        else
        {
            this.filters = null;
        }
    }

    /**
     * Constructs the sub-parameters for a funnel step.
     * @return A jsonifiable Map containing the step's request parameters.
     */
    @Override
    public Object constructParameterRequestArgs() {
        HashMap<String, Object> args = new HashMap<String, Object>();
        
        // Add required step parameters
        args.put(KeenQueryConstants.EVENT_COLLECTION, eventCollection);
        args.put(KeenQueryConstants.ACTOR_PROPERTY, actorProperty);
        
        // timeframe is only required if not specified for the funnel itself,
        // so it may not be set.
        if (null != this.timeframe)
        {
            args.putAll(timeframe.constructTimeframeArgs());
        }

        // Add optional parameters if they've been specified.
        if (null != this.filters)
        {
            args.put(
                KeenQueryConstants.FILTERS,
                this.filters.constructParameterRequestArgs()
            );
        }
        
        return args;
    }
    
}
