import json
import logging
from datetime import datetime
from google.cloud import datastore

from flask import escape

def tea_scan_data(request):
    """HTTP Cloud Function.
    Args:
        request (flask.Request): The request object.
        <http://flask.pocoo.org/docs/1.0/api/#flask.Request>
    Returns:
        The response text, or any set of values that can be turned into a
        Response object using `make_response`
        <http://flask.pocoo.org/docs/1.0/api/#flask.Flask.make_response>.
    """
    op_datetime = datetime.utcnow()
    request_json = request.get_json(silent=True)
    request_args = request.args
    
    logging.info("RECEIVED REQUEST")
    logging.info(request_json)
    
    # The kind for the new entity
    kind = 'tea_scan_data'
    
    request_result = {
        'result' : 'OK',
        'count' : 0,
        'id_list' : []
    }
    
    request_json = request.get_json(silent=True)
    if request_json is None:
        logging.info("request_json is None")
        
    if request_json and 'data_type' in request_json and 'data' in request_json and request_json['data_type'] == kind:
        try:
            dataList = request_json['data']
            logging.info(dataList)
            logging.info(len(dataList))
            
            if len(dataList) <= 0:
                return json.dumps(request_result)
                
            # Instantiates a client
            db = datastore.Client(namespace="hci")
            for rec in dataList:
                # Prepares key of new entity
                entity_key = db.key(kind)
                
                # Prepares the new entity
                new_entity = datastore.Entity(key=entity_key)
                new_entity['tag'] = rec['tag']
                new_entity['data'] = rec['data']
                new_entity['cre_dt'] = rec['cre_dt']
                
                # Saves the entity
                db.put(new_entity)
                
                request_result['id_list'].append(rec['id'])
                logging.info("Processed id {0}".format(rec['id']))
            
            # data_type = request_json['data_type']
            # logging.info(data_type)
        except Exception as e:
            logging.error(e)
        # dataList = request_json['data']
        # and request_json['request_json'] == kind
        # if len(dataList) <= 0:
        #   logging.info("request_json <= 0")
        logging.info("IF OK")
    else:
        logging.info("IF FAILURE")
        
    request_result['count'] = len(request_result['id_list'])
    return json.dumps(request_result)